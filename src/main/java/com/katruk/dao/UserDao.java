package com.katruk.dao;

import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import java.util.Collection;

public interface UserDao {

  Collection<User> getAllUser() throws DaoException;

  User getUserByUsername(final String username) throws DaoException;

  User getUserById(final Long userId) throws DaoException;

  User save(final User user) throws DaoException;
}
