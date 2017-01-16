package com.katruk.dao.mysql;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.dao.UserDao;
import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

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

  public UserDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(UserDaoMySql.class);
  }

  @Override
  public Collection<User> getAllUser() throws DaoException {
    final Collection<User> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_USER))) {
        result = getUserByStatement(statement);
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
  public Optional<User> getUserByUsername(final String username) throws DaoException {
    final Optional<User> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_USER_BY_USERNAME))) {
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
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_USER_BY_ID))) {
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
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.REPLACE_USER))) {
        statement.setLong(1, user.getPerson().getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        // TODO: 06.01.17 do simple
        statement.setString(4, user.getRole() != null ? user.getRole().name() : null);
        int affectedRows = statement.executeUpdate();
        connection.commit();
        if (affectedRows == 0) {
          throw new SQLException("Replace user failed, no rows affected.");
        }
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
      connection.setAutoCommit(true);
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
        user.setId(person.getId());
        user.setPerson(person);
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        if (nonNull(resultSet.getString("role"))) {
          user.setRole(User.Role.valueOf(resultSet.getString("role")));
        }
        result.add(user);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}