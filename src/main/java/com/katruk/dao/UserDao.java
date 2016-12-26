package com.katruk.dao;

import com.katruk.exception.DaoException;
import com.katruk.entity.User;

public interface UserDao{

  User getUserByUsername(String username) throws DaoException;

  void create(User user) throws DaoException;

}
