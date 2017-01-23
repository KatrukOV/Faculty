package com.katruk.dao.mysql;

import com.katruk.dao.UserDao;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class UserDaoMySqlCache implements UserDao {

  private final Logger logger;
  private final UserDaoMySql userDaoMySql;
  private final Map<Long, User> users;

  public UserDaoMySqlCache() throws DaoException {
    this.logger = Logger.getLogger(UserDaoMySqlCache.class);
    this.userDaoMySql = new UserDaoMySql();
    this.users = loadUser();
  }

  private Map<Long, User> loadUser() throws DaoException {
    Collection<User> allUser = this.userDaoMySql.allUser();
    Map<Long, User> result = new HashMap<>();
    for (User user : allUser) {
      this.users.put(user.id(), user);
    }
    return result;
  }

  @Override
  public Collection<User> allUser() throws DaoException {
    return this.users.values();
  }

  @Override
  public User findUserByUsername(final String username) throws DaoException {
    for (User user : this.users.values()) {
      if (user.username().equals(username)) {
        return user;
      }
    }
    String message = String.format("User not found by username: %s.", username);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public User findUserById(final Long userId) throws DaoException {
    for (User user : this.users.values()) {
      if (user.id().equals(userId)) {
        return user;
      }
    }
    String message = String.format("User not found by id: %d.", userId);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public User save(final User user) throws DaoException {
    User userInDB = this.userDaoMySql.save(user);
    this.users.remove(userInDB.id());
    this.users.put(userInDB.id(), userInDB);
    return userInDB;
  }
}