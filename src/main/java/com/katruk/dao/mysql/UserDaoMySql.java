package com.katruk.dao.mysql;

import com.katruk.dao.PersonDao;
import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.duplCode.CheckExecuteUpdate;
import com.katruk.dao.mysql.duplCode.GetUser;
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
import java.util.NoSuchElementException;

public final class UserDaoMySql implements UserDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;
  private final PersonDao personDao;

  public UserDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(UserDaoMySql.class);
    personDao = new PersonDaoMySql();
  }

  @Override
  public Collection<User> getAllUser() throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_USER))) {
      return getUserByStatement(statement);
    } catch (SQLException e) {
      logger.error("Cannot load all users.", e);
      throw new DaoException("Cannot load all users.", e);
    }
  }

  @Override
  public User getUserByUsername(final String username) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_USER_BY_USERNAME))) {
      statement.setString(1, username);
      return getUserByStatement(statement).stream().iterator().next();
    } catch (SQLException e) {
      String message = String.format("User not found by username: %s.", username);
      logger.info(message);
      throw new DaoException(message, new NoSuchElementException());
    }
  }

  @Override
  public User getUserById(final Long userId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_USER_BY_ID))) {
      statement.setLong(1, userId);
      return getUserByStatement(statement).stream().iterator().next();
    } catch (SQLException e) {
      String message = String.format("User not found by id: %d.", userId);
      logger.info(message);
      throw new DaoException(message, new NoSuchElementException());
    }
  }

  @Override
  public User save(final User user) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      saveAndCommitOrRollback(user, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot save user.", e);
      throw new DaoException("Cannot save user.", e);
    }
    return user;
  }

  private void saveAndCommitOrRollback(User user, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.REPLACE_USER))) {
      fillSaveUserStatement(user, statement);
      new CheckExecuteUpdate(statement, "Replace user failed, no rows affected.").check();
      this.personDao.save(user.getPerson());
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillSaveUserStatement(User user, PreparedStatement statement) throws SQLException {
    statement.setLong(1, user.getPerson().getId());
    statement.setString(2, user.getUsername());
    statement.setString(3, user.getPassword());
    statement.setString(4, user.getRole() != null ? user.getRole().name() : null);
  }

  private Collection<User> getUserByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<User> users = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        User user = new GetUser(resultSet).get();
        users.add(user);
      }
    } catch (SQLException e) {
      logger.error("Cannot get user by statement.", e);
      throw new DaoException("Cannot get user by statement.", e);
    }
    if (users.isEmpty()) {
      throw new DaoException("No users by statement.", new NoSuchElementException());
    }
    return users;
  }

}