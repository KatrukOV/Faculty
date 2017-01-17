package com.katruk.web.controller.commands;

import com.katruk.converter.UserConverter;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.ServiceException;
import com.katruk.exception.ValidateException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.util.UserValidator;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Registration implements Command, PageAttribute {

  private final Logger logger;
  private final UserService userService;
  private final UserValidator userValidator;
  private final static String REGISTRATION_OK = "User successfully registered";

  public Registration() {
    this.logger = Logger.getLogger(Registration.class);
    this.userService = new UserServiceImpl();
    this.userValidator = new UserValidator();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.INDEX);
    UserDto userDto = getUserDtoFromRequest(request);
    try {
      this.userValidator.validate(userDto);
    } catch (ValidateException e) {
      request.setAttribute(ERROR, e.getMessage());
      logger.error(e);
      return PageConfig.getInstance().getValue(PageConfig.REGISTRATION);
    }
    try {
      User user = new UserConverter().convertToUser(userDto);
      this.userService.save(user);
    } catch (ServiceException e) {
      request.setAttribute(ERROR, e.getMessage());
      logger.error(e);
      return PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
    }
    request.setAttribute(INFO, REGISTRATION_OK);
    logger.info(REGISTRATION_OK);
    return page;
  }

  private UserDto getUserDtoFromRequest(final HttpServletRequest request) {
    final UserDto userDto = new UserDto();
    userDto.setLastName(request.getParameter(LAST_NAME));
    userDto.setName(request.getParameter(NAME));
    userDto.setPatronymic(request.getParameter(PATRONYMIC));
    userDto.setUsername(request.getParameter(USERNAME));
    userDto.setPassword(request.getParameter(PASSWORD));
    userDto.setConfirmPassword(request.getParameter(CONFIRM_PASSWORD));
    return userDto;
  }
}