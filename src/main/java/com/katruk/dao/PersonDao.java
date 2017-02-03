package com.katruk.dao;

import com.katruk.entity.Person;
import com.katruk.exception.DaoException;

import java.util.Collection;

public interface PersonDao {

  Person getPersonById(final Long personId) throws DaoException;

  Collection<Person> getAllPerson() throws DaoException;

  Person save(final Person person) throws DaoException;
}
