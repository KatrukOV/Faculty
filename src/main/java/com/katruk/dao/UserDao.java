package com.katruk.dao;

import com.katruk.exception.DaoException;
import com.katruk.entity.User;

import java.util.Optional;

public interface UserDao{

  Optional<User> getUserByUsername(String username) throws DaoException;

  User save(User user) throws DaoException;

}
