package com.katruk.dao.mysql;

import com.katruk.dao.UserDao;
import com.katruk.exception.DaoException;
import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.util.ConnectionPool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoMySql implements UserDao {

  private final ConnectionPool connectionPool;
//  private final Logger logger;

  private final String CREATE_PERSON =
      "INSERT INTO person (last_name, name, patronymic) VALUES (?, ?, ?);";

  private final String GET_PERSON_ID =
      "SELECT id "
      + "FROM person "
      + "WHERE last_name = ? AND name = ? AND patronymic = ?"
      + "ORDER BY id DESC"
      + "LIMIT 1;";

  private final String CREATE_USER =
      "INSERT INTO user (person_id, username, password, role) VALUES (?, ?, ?, ?);";

  private final String GET_USER_BY_USERNAME =
      "SELECT p.last_name, p.name, p.patronymic, u.username, u.password, u.role"
      + "FROM user AS u"
      + "INNER JOIN person AS p"
      + "ON u.person_id = p.id"
      + "WHERE u.username = ?;";

  public UserDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
//    this.logger = Logger.getLogger(UserDaoMySql.class);
  }

  public User getUserByUsername(String username) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME)) {
//        statement.setString(1, username);
        return getUserByStatement(statement);
      } catch (SQLException e) {
        connection.rollback();
//        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
//      logger.error("", e);
      throw new DaoException("", e);
    }
  }


  public void create(User user) throws DaoException {
    Person person = user.getPerson();
    try (Connection connection = this.connectionPool.getConnection()) {
      savePerson(connection, person);
      saveUser(connection, person, user);
      connection.commit();
    } catch (SQLException e) {
//      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  private void savePerson(Connection connection, Person person) throws DaoException, SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_PERSON)) {
      statement.setString(1, person.getLastName());
      statement.setString(2, person.getName());
      statement.setString(3, person.getPatronymic());
      statement.execute();
    } catch (SQLException e) {
      connection.rollback();
//      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  private void saveUser(Connection connection, Person person, User user)
      throws DaoException, SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
      statement.setLong(1, getPersonId(connection, person));
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getRole().name());
      statement.execute();
    } catch (SQLException e) {
      connection.rollback();
//      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  private long getPersonId(Connection connection, Person person) throws DaoException, SQLException {
    try (PreparedStatement statement = connection.prepareStatement(GET_PERSON_ID)) {
      statement.setString(1, person.getLastName());
      statement.setString(2, person.getName());
      statement.setString(3, person.getPatronymic());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getLong("id");
      }
    } catch (SQLException e) {
      connection.rollback();
//      logger.error("", e);
      throw new DaoException("", e);
    }
    //todo
    return person.getId();
  }

  private User getUserByStatement(PreparedStatement statement) throws DaoException {
    try (ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        User user = new User();
        Person person = new Person();
        person.setLastName(resultSet.getString("last_name"));
        person.setName(resultSet.getString("name"));
        person.setPatronymic(resultSet.getString("patronymic"));
        user.setPerson(person);
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        return user;
      }
    } catch (SQLException e) {
//      logger.error("", e);
      throw new DaoException("", e);
    }
    // TODO: return
    return null;
//    throw new DaoException("User not exist");
  }
}
