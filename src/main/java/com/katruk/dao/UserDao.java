package com.katruk.dao;

import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

  Collection<User> getAllUser() throws DaoException;

  Optional<User> getUserByUsername(final String username) throws DaoException;

  Optional<User> getUserById(final Long userId) throws DaoException;

  User save(final User user) throws DaoException;
}
