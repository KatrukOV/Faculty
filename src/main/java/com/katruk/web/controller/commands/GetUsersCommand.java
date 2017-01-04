package com.katruk.web.controller.commands;

import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.ServiceException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.converter.UserConverter;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUsersCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final UserService userService;

  public GetUsersCommand() {
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(GetUsersCommand.class);
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {

    String page = request.getContextPath();
    try {
      Collection<User> users = this.userService.getAll();
      List<UserDto> userList = new UserConverter().convertToDto(users);
      request.setAttribute(USER_LIST, userList);
//      page = Config.getInstance().getValue(Config.ALL_USERS);
      logger.info(String.format("get all users = %d", userList.size()));
    } catch (ServiceException e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all users", e);
    }
    return page;
  }
}
