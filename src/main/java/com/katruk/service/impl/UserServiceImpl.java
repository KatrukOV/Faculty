package com.katruk.service.impl;

import com.katruk.dao.PersonDao;
import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.PersonDaoMySql;
import com.katruk.dao.mysql.UserDaoMySql;
import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;

public final class UserServiceImpl implements UserService {

  private final Logger logger;
  private final PersonDao personDao;
  private final UserDao userDao;

  public UserServiceImpl() {
    this.logger = Logger.getLogger(UserServiceImpl.class);
    this.userDao = new UserDaoMySql();
    this.personDao = new PersonDaoMySql();
  }

  @Override
  public Collection<User> getAll() throws ServiceException {
    Collection<User> result;
    try {
      result = this.userDao.getAllUser();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }

  @Override
  public User getUserByUsername(final String username) throws ServiceException {
    final User user;
    try {
      user = this.userDao.getUserByUsername(username)
          .orElseThrow(() -> new DaoException("User not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final Person person;
    try {
      person = this.personDao.getPersonById(user.getPerson().getId())
          .orElseThrow(() -> new DaoException("Person not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    user.setPerson(person);
    return user;
  }

  @Override
  public User getUserById(final Long userId) throws ServiceException {
    final User user;
    try {
      user = this.userDao.getUserById(userId)
          .orElseThrow(() -> new DaoException("User not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final Person person;
    try {
      person = this.personDao.getPersonById(user.getPerson().getId())
          .orElseThrow(() -> new DaoException("Person not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    user.setId(person.getId());
    user.setPerson(person);
    return user;
  }

  @Override
  public User save(final User user) throws ServiceException {
    final Person person = user.getPerson();
    try {
      this.personDao.save(person);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    user.setPerson(person);
    user.setId(person.getId());
    try {
      this.userDao.save(user);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return user;
  }
}
