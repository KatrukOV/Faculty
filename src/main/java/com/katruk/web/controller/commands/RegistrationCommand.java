package com.katruk.web.controller.commands;

import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ValidateException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.util.UserValidator;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegistrationCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final UserService userService;
  private final UserValidator userValidator;
  private final String REGISTRATION_OK = "User successfully registered";

  public RegistrationCommand() {
    this.logger = Logger.getLogger(RegistrationCommand.class);
    this.userService = new UserServiceImpl();
    this.userValidator = new UserValidator();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.INDEX);
    UserDto userDto = (UserDto) request.getAttribute(USER_DTO);
    try {
      this.userValidator.validate(userDto);
    } catch (ValidateException e) {
      request.setAttribute(ERROR, e.getMessage());
      logger.error(e);
      page = Config.getInstance().getValue(Config.REGISTRATION);
    }
    try {
      this.userService.create(userDto);
    } catch (DaoException e) {
      request.setAttribute(ERROR, e.getMessage());
      logger.error(e);
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
    }
    request.setAttribute(INFO, REGISTRATION_OK);
    logger.info(REGISTRATION_OK);
    return page;
  }
}