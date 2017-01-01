package com.katruk.service;

import com.katruk.entity.Subject;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.exception.ValidateException;

import java.util.List;

public interface UserService {

  User getUserByUsername(final String username) throws ServiceException;

  User getUserById(final Long userId) throws ServiceException;

  User create(final UserDto userDto) throws ServiceException;
}
