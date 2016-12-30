package com.katruk.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.UserDaoMySql;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.exception.ValidateException;
import com.katruk.service.UserService;
import com.katruk.util.Converter;
import com.katruk.util.UserValidator;
import com.katruk.web.controller.commands.LoginCommand;

import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {

  private final Logger logger;
  private final UserDao userDao;
  private final Converter converter;

  public UserServiceImpl() {
    this.logger = Logger.getLogger(UserServiceImpl.class);
    this.userDao = new UserDaoMySql();
    this.converter = new Converter();
  }

  public User create(UserDto userDto) throws ServiceException {
    User user = this.converter.convertDto(userDto);
    try {
      this.userDao.create(user);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return user;
  }

  public User getUserByUsername(String username) throws ServiceException {
    User user;
    try {
      user = this.userDao.getUserByUsername(username);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return user;
  }
}
