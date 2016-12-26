package com.katruk.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.UserDaoMySql;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ValidateException;
import com.katruk.service.UserService;
import com.katruk.util.Converter;
import com.katruk.util.UserValidator;

public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final Converter converter;

  public UserServiceImpl() {
    this.userDao = new UserDaoMySql();
    this.converter = new Converter();
  }

  public User create(UserDto userDto) throws DaoException {
    User user = this.converter.convertDto(userDto);
    this.userDao.create(user);
    return user;
  }

  public UserDto getUserByUsername(String username) throws DaoException {
    User user = this.userDao.getUserByUsername(username);
    return this.converter.convertUser(user);
  }
}
