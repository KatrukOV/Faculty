package com.katruk.util;

import static java.util.Objects.isNull;

import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ValidateException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;

public class UserValidator {

  private final int minSizeLastName = 5;
  private final int minSizeName = 3;
  private final int minSizePatronymic = 6;
  private final int minSizeUsername = 4;
  private final int minSizePassword = 4;
  private final UserService userService;

  public UserValidator() {
    this.userService = new UserServiceImpl();
  }

  public void validate(UserDto userDto) throws ValidateException {
    requiredField(userDto);
    validatePerson(userDto);
    validateUsername(userDto);
    validatePassword(userDto);
    existsUser(userDto);
  }

  private void requiredField(UserDto userDto) throws ValidateException {
    if (isNull(userDto.getLastName()) || isNull(userDto.getName())
        || isNull(userDto.getPatronymic()) || isNull(userDto.getUsername())
        || isNull(userDto.getPassword()) || isNull(userDto.getRole())) {
      throw new ValidateException("Same field is empty");
    }
  }

  private void validatePerson(UserDto userDto) throws ValidateException {
    if (userDto.getLastName().length() < minSizeLastName) {
      throw new ValidateException(
          String.format("LastName is short (mast be > %d)", minSizeLastName));
    }
    if (userDto.getName().length() < minSizeName) {
      throw new ValidateException(
          String.format("Name is short (mast be > %d)", minSizeName));
    }
    if (userDto.getPatronymic().length() < minSizePatronymic) {
      throw new ValidateException(
          String.format("Patronymic is short (mast be > %d)", minSizePatronymic));
    }
  }

  private void validateUsername(UserDto userDto) throws ValidateException {
    if (userDto.getUsername().length() < minSizeUsername) {
      throw new ValidateException(
          String.format("Username is short (mast be > %d)", minSizeUsername));
    }
  }

  private void validatePassword(UserDto userDto) throws ValidateException {
    if (userDto.getPassword().length() < minSizePassword) {
      throw new ValidateException(
          String.format("Password is short (mast be > %d)", minSizePassword));
    }
    if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
      throw new ValidateException("Passwords don't match");
    }
  }

  private void existsUser(UserDto userDto) throws ValidateException {
    try {
      this.userService.getUserByUsername(userDto.getUsername());
    } catch (DaoException e) {
      // TODO: 16.12.2016 log
      return;
    }
    // TODO: 16.12.2016 log
    throw new ValidateException(
        String.format("User with username %s exists", userDto.getUsername()));

  }
}
