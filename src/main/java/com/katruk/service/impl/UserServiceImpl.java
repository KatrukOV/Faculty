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

import java.util.ArrayList;
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
      users = this.userDao.allUser();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    Collection<Person> persons = this.personService.getAll();
//    for (User user : users) {
//      persons.stream().filter(person -> Objects.equals(user.id(), person.id()))
//          .forEach(user::changePerson);
//    }
    Collection<User> results = new ArrayList<>();
    for (User user : users) {
      for (Person person : persons) {
        if(Objects.equals(user.id(), person.id())){
          results.add(user.changePerson(person));
        }
      }
    }
    return results;
  }

  @Override
  public User getUserByUsername(final String username) throws ServiceException {
    final User user;
    try {
      user = this.userDao.findUserByUsername(username)
          .orElseThrow(() -> new DaoException("User not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final Person person = this.personService.getPersonById(user.person().id());
    return user.changePerson(person);
  }

  @Override
  public User getUserById(final Long userId) throws ServiceException {
    final User user;
    try {
      user = this.userDao.findUserById(userId)
          .orElseThrow(() -> new DaoException("User not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final Person person = this.personService.getPersonById(user.person().id());
    return user.changePerson(person);
  }

  @Override
  public User save(final User user) throws ServiceException {
    final Person person = this.personService.save(user.person());
    final User userForSave =
        new User(person.id(), person, user.username(), user.password(), user.role());
    try {
      return this.userDao.save(userForSave);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
  }
}