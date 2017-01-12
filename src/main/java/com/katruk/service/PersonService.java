package com.katruk.service;

import com.katruk.entity.Person;
import com.katruk.exception.ServiceException;
import java.util.Collection;

public interface PersonService {

  Person getPersonById(final Long personId) throws ServiceException;

  Person save(final Person person) throws ServiceException;

  Collection<Person> getAll() throws ServiceException;
}
