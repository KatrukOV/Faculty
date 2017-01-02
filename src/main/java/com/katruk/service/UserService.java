package com.katruk.service;

import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.ServiceException;

public interface UserService {

  User getUserByUsername(final String username) throws ServiceException;

  User getUserById(final Long userId) throws ServiceException;

  User create(final UserDto userDto) throws ServiceException;
}
