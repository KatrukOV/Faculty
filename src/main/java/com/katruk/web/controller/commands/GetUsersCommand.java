package com.katruk.web.controller.commands;

import com.katruk.converter.UserConverter;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.ServiceException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetUsersCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final UserService userService;

  public GetUsersCommand() {
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(GetUsersCommand.class);
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ALL_USERS);
/*
    System.out.println(">>> page1 =" + page);
    String page2 = request.getContextPath();
    System.out.println(">>> page2 =" + page2);

    String url = request.getRequestURL().toString();
    String queryString = request.getQueryString();
    System.out.println(">>>>>>>>>>>= " + url + "  ?== " + queryString);
    String servletPath = request.getServletPath();
    System.out.println(">>>>>>>>>>> servletPath= " + servletPath);
*/
    try {
      Collection<User> users = this.userService.getAll();
      System.out.println(">>> Coll users=" + users);
      List<UserDto> userList = new UserConverter().convertToDto(users);
      request.setAttribute(USER_LIST, userList);
      System.out.println(">>> UsersDto=" + userList);
      logger.info(String.format("get all users = %d", userList.size()));
    } catch (ServiceException e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all users", e);
    }
    return page;
  }
}
