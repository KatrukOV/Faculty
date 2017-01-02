package com.katruk.dao.mysql;

import com.katruk.dao.UserDao;
import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class UserDaoMySql implements UserDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final String CREATE_USER =
      "INSERT INTO user (person_id, username, password, role) "
      + "VALUES (?, ?, ?, ?);";

  private final String GET_USER_BY_USERNAME =
      "SELECT u.person_id, u.username, u.password, u.role "
      + "FROM user AS u "
      + "WHERE u.username = ? "
      + "ORDER BY u.person_id DESC "
      + "LIMIT 1;";

  private final String GET_USER_BY_ID =
      "SELECT u.person_id, u.username, u.password, u.role "
      + "FROM user AS u "
      + "WHERE u.person_id = ? "
      + "ORDER BY u.person_id DESC "
      + "LIMIT 1;";

  public UserDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(UserDaoMySql.class);
  }

  @Override
  public Optional<User> getUserByUsername(final String username) throws DaoException {
    final Optional<User> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME)) {
        statement.setString(1, username);
        result = getUserByStatement(statement).stream().findFirst();
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }

  @Override
  public Optional<User> getUserById(final Long userId) throws DaoException {
    final Optional<User> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
        statement.setLong(1, userId);
        result = getUserByStatement(statement).stream().findFirst();
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }


  @Override
  public User save(final User user) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
        statement.setLong(1, user.getPerson().getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole().name());
        statement.execute();
        connection.commit();
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return user;
  }

  private Collection<User> getUserByStatement(final PreparedStatement statement)
      throws DaoException {
    Collection<User> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        User user = new User();
        Person person = new Person();
        person.setId(resultSet.getLong("person_id"));
        user.setPerson(person);
        user.setId(person.getId());
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        result.add(user);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}