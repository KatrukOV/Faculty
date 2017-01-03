package com.katruk.web.controller.commands;

import com.katruk.entity.Student;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.ServiceException;
import com.katruk.exception.ValidateException;
import com.katruk.service.StudentService;
import com.katruk.service.UserService;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.util.Converter;
import com.katruk.util.UserValidator;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class RegistrationCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final UserService userService;
  private final StudentService studentService;
  private final UserValidator userValidator;
  private final static String REGISTRATION_OK = "User successfully registered";

  public RegistrationCommand() {
    this.logger = Logger.getLogger(RegistrationCommand.class);
    this.userService = new UserServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.userValidator = new UserValidator();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    System.out.println(">>> Begin reg");
    String page = Config.getInstance().getValue(Config.INDEX);
//    UserDto userDto = (UserDto) request.getAttribute(USER_DTO);
    UserDto userDto = getUserDtoFromRequest(request);

    System.out.println(">>> userDto=" + userDto);

    try {
      this.userValidator.validate(userDto);
      System.out.println(">>> no valid err");
    } catch (ValidateException e) {
      request.setAttribute(ERROR, e.getMessage());
      logger.error(e);
      page = Config.getInstance().getValue(Config.REGISTRATION);
    }
    try {
      User user = new Converter().convertDto(userDto);
      user = this.userService.create(user);
      Student student = new Student();
      System.out.println(">>> user save=" + user);
      student.setUser(user);
      this.studentService.create(student);

    } catch (ServiceException e) {
      request.setAttribute(ERROR, e.getMessage());
      logger.error(e);
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
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
    userDto.setRole(User.Role.STUDENT.name());
    return userDto;
  }
}