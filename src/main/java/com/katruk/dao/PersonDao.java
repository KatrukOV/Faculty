package com.katruk.dao;

import com.katruk.entity.Person;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import java.util.Optional;

public interface PersonDao {

  Optional<Person> getPersonById(final Long personId) throws DaoException;

  Person save(final Person person) throws DaoException;

}
