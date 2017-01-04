package com.katruk.web.controller.commands;

import com.katruk.converter.UserConverter;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
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

public class SetRoleCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final UserService userService;

  public SetRoleCommand() {
    this.logger = Logger.getLogger(SetRoleCommand.class);
    this.userService = new UserServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ALL_USERS);
    try {
      User.Role role = User.Role.valueOf(request.getParameter(ROLE));
      System.out.println(">>>>>>>>>>>> role= " + role);
      Long userId = Long.parseLong(request.getParameter(USER_ID));
      User user = this.userService.getUserById(userId);
      System.out.println(">>>>>>>>>>>> user= " + user);
      user.setRole(role);
      this.userService.save(user);
      Collection<User> users = this.userService.getAll();
      List<UserDto> userList = new UserConverter().convertToDto(users);
      request.setAttribute(USER_LIST, userList);
      logger.info(String.format("set role=%s for user= %s", role, user));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable set role for human", e);
    }
    return page;
  }
}

