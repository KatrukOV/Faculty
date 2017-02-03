package com.katruk.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.UserDaoMySql;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;

public final class UserServiceImpl implements UserService {

  private final Logger logger;
  private final UserDao userDao;

  public UserServiceImpl() {
    this.logger = Logger.getLogger(UserServiceImpl.class);
    this.userDao = new UserDaoMySql();
  }

  @Override
  public Collection<User> getAll() throws ServiceException {
    try {
      return this.userDao.getAllUser();
    } catch (DaoException e) {
      String message = "Cannot get all users.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public User getUserByUsername(final String username) throws ServiceException {
    try {
      return this.userDao.getUserByUsername(username);
    } catch (DaoException e) {
      String message = String.format("Cannot get user by username: %s.", username);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public User getUserById(final Long userId) throws ServiceException {
    try {
      return this.userDao.getUserById(userId);
    } catch (DaoException e) {
      String message = String.format("Cannot get user by id: %d.", userId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public User save(final User user) throws ServiceException {
    try {
      return this.userDao.save(user);
    } catch (DaoException e) {
      String message = "Cannot save user.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }
}