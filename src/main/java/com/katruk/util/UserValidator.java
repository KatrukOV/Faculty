package com.katruk.util;

import static java.util.Objects.isNull;

import com.katruk.entity.dto.UserDto;
import com.katruk.exception.ServiceException;
import com.katruk.exception.ValidateException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;

import org.apache.log4j.Logger;

public final class UserValidator {

  private final UserService userService;
  private final Logger logger;

  private final static int MIN_SIZE_LAST_NAME = 4;
  private final static int MIN_SIZE_NAME = 3;
  private final static int MIN_SIZE_PATRONYMIC = 5;
  private final static int MIN_SIZE_USERNAME = 3;
  private final static int MIN_SIZE_PASSWORD = 4;

  public UserValidator() {
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(UserValidator.class);
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
        || isNull(userDto.getPassword())) {
      String message = "Same field is empty";
      logger.info(message);
      throw new ValidateException(message);
    }
  }

  private void validatePerson(UserDto userDto) throws ValidateException {
    if (userDto.getLastName().length() < MIN_SIZE_LAST_NAME) {
      String message = String.format("LastName is short (mast be > %d)", MIN_SIZE_LAST_NAME);
      logger.info(message);
      throw new ValidateException(message);
    }
    if (userDto.getName().length() < MIN_SIZE_NAME) {
      String message = String.format("Name is short (mast be > %d)", MIN_SIZE_NAME);
      logger.info(message);
      throw new ValidateException(message);
    }
    if (userDto.getPatronymic().length() < MIN_SIZE_PATRONYMIC) {
      String message = String.format("Patronymic is short (mast be > %d)", MIN_SIZE_PATRONYMIC);
      logger.info(message);
      throw new ValidateException(message);
    }
  }

  private void validateUsername(UserDto userDto) throws ValidateException {
    if (userDto.getUsername().length() < MIN_SIZE_USERNAME) {
      String message = String.format("Username is short (mast be > %d)", MIN_SIZE_USERNAME);
      logger.info(message);
      throw new ValidateException(message);
    }
  }

  private void validatePassword(UserDto userDto) throws ValidateException {
    if (userDto.getPassword().length() < MIN_SIZE_PASSWORD) {
      String message = String.format("Password is short (mast be > %d)", MIN_SIZE_PASSWORD);
      logger.info(message);
      throw new ValidateException(message);
    }
    if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
      String message = "Passwords don't match";
      logger.info(message);
      throw new ValidateException(message);
    }
  }

  private void existsUser(UserDto userDto) throws ValidateException {
    try {
      this.userService.getUserByUsername(userDto.getUsername());
    } catch (ServiceException e) {
      logger.debug(String.format("User with USERNAME %s not exists yet", userDto.getUsername()));
      return;
    }
    String message = String.format("User with USERNAME %s exists", userDto.getUsername());
    logger.info(message);
    throw new ValidateException(message);
  }
}
