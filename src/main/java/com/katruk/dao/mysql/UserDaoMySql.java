package com.katruk.dao.mysql;

import com.katruk.dao.PersonDao;
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
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserDaoMySql implements UserDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final String CREATE_USER =
      "INSERT INTO user (person_id, username, password, role) "
      + "VALUES (?, ?, ?, ?);";

  private final String GET_USER_BY_USERNAME =
      "SELECT p.id, p.last_name, p.name, p.patronymic, u.username, u.password, u.role "
      + "FROM user AS u "
      + "INNER JOIN person AS p "
      + "ON u.person_id = p.id "
      + "WHERE u.username = ? "
      + "ORDER BY p.id DESC "
      + "LIMIT 1;";

  public UserDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(UserDaoMySql.class);
  }

  @Override
  public Optional<User> getUserByUsername(String username) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME)) {
        statement.setString(1, username);
        return Optional.of(getUserByStatement(statement));
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public User save(User user) throws DaoException {
    Person person = user.getPerson();
    try (Connection connection = this.connectionPool.getConnection()) {
      saveUser(connection, user);
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return user;
  }


  private void saveUser(Connection connection, User user)
      throws DaoException, SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
      statement.setLong(1, user.getPerson().getId());
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getRole().name());
      statement.execute();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("", e);
      throw new DaoException("", e);
    }
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
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        return user;
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    throw new DaoException("User not exist", new NoSuchElementException());
  }
}
