package com.katruk.dao;

import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

  Collection<User> allUser() throws DaoException;

  User findUserByUsername(final String username) throws DaoException;

  User findUserById(final Long userId) throws DaoException;

  User save(final User user) throws DaoException;
}
