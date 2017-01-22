package com.katruk.dao.mysql;

import static java.util.Objects.nonNull;

import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
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

public final class UserDaoMySql implements UserDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public UserDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(UserDaoMySql.class);
  }

  @Override
  public Collection<User> getAllUser() throws DaoException {
    Collection<User> result;
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_USER))) {
      result = getUserByStatement(statement);
    } catch (SQLException e) {
      logger.error("Cannot get all users.", e);
      throw new DaoException("Cannot get all users.", e);
    }
    return result;
  }


  @Override
  public Optional<User> getUserByUsername(final String username) throws DaoException {
    final Optional<User> result;
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_USER_BY_USERNAME))) {
      statement.setString(1, username);
      result = getUserByStatement(statement).stream().findFirst();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get user by username: %s.", username), e);
      throw new DaoException(String.format("Cannot get user by username: %s.", username), e);
    }
    return result;
  }

  @Override
  public Optional<User> getUserById(final Long userId) throws DaoException {
    final Optional<User> result;
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_USER_BY_ID))) {
      statement.setLong(1, userId);
      result = getUserByStatement(statement).stream().findFirst();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get user by id: %d.", userId), e);
      throw new DaoException(String.format("Cannot get user by id: %d.", userId), e);
    }
    return result;
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
    Collection<User> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        User user = getUser(resultSet);
        result.add(user);
      }
    } catch (SQLException e) {
      logger.error("Cannot get user by statement.", e);
      throw new DaoException("Cannot get user by statement.", e);
    }
    return result;
  }

  private User getUser(ResultSet resultSet) throws SQLException {
    User user = new User(person, username, password, role);
    Person person = new Person(lastName, name, patronymic);
    person.setId(resultSet.getLong(PERSON_ID));
    user.setId(person.getId());
    user.setPerson(person);
    user.setUsername(resultSet.getString(USERNAME));
    user.setPassword(resultSet.getString(PASSWORD));
    if (nonNull(resultSet.getString(ROLE))) {
      user.setRole(User.Role.valueOf(resultSet.getString(ROLE)));
    }
    return user;
  }
}