package com.katruk.service.impl;

import com.katruk.dao.PersonDao;
import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.PersonDaoMySql;
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

public final class PersonServiceImpl implements PersonService {

  private final Logger logger;
  private final PersonDao personDao;

  public PersonServiceImpl() {
    this.logger = Logger.getLogger(PersonServiceImpl.class);
    this.personDao = new PersonDaoMySql();
  }


  @Override
  public Person getPersonById(Long personId) throws ServiceException {
    final Person person;
    try {
      person = this.personDao.getPersonById(personId)
          .orElseThrow(() -> new DaoException("Person not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return person;
  }

  @Override
  public Person save(Person person) throws ServiceException {
    try {
      person = this.personDao.save(person);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return person;
  }

  @Override
  public Collection<Person> getAll() throws ServiceException {
    Collection<Person> persons;
    try {
      persons = this.personDao.getAllPerson();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return persons;
  }
}