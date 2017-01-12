package com.katruk.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.UserDaoMySql;
import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.PersonService;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class UserServiceImpl implements UserService {

  private final Logger logger;
  private final PersonService personService;
  private final UserDao userDao;

  public UserServiceImpl() {
    this.logger = Logger.getLogger(UserServiceImpl.class);
    this.userDao = new UserDaoMySql();
    this.personService = new PersonServiceImpl();
  }

  @Override
  public Collection<User> getAll() throws ServiceException {
    Collection<User> users;
    try {
      users = this.userDao.getAllUser();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    Collection<Person> persons = this.personService.getAll();
    for (User user : users) {
      for (Person person : persons) {
        if (Objects.equals(user.getId(), person.getId())) {
          user.setPerson(person);
        }
      }
    }
    return users;
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
    final Person person = this.personService.getPersonById(user.getPerson().getId());
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
    final Person person = this.personService.getPersonById(user.getPerson().getId());
    user.setPerson(person);
    return user;
  }

  @Override
  public User save(final User user) throws ServiceException {
    System.out.println("?? user=" + user);
    final Person person = this.personService.save(user.getPerson());
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