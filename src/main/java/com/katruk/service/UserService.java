package com.katruk.service;

import com.katruk.entity.Subject;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ValidateException;

import java.util.List;

public interface UserService {

  User create(UserDto userDto) throws DaoException;

  UserDto getUserByUsername(String username) throws DaoException;

}
