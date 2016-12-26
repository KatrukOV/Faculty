package com.katruk.web.controller.commands;

import static java.util.Objects.isNull;

import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ICommand, PageAttribute {

  private final String ERROR_LOGIN_EMPTY = "Username or password is empty";
  private final String ERROR_LOGIN_WRONG = "Wrong username or password";
  private final int MaxInactiveInterval = 60 * 60 * 24;
  private final Logger logger;
  private final UserService userService;

  public LoginCommand() {
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(LoginCommand.class);
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {

    final String username = request.getParameter(USERNAME);
    final String password = request.getParameter(PASSWORD);
    final HttpSession session = request.getSession();
    String page = Config.getInstance().getValue(Config.INDEX);

    if (isNull(username) || isNull(password)) {
      request.setAttribute(ERROR, ERROR_LOGIN_EMPTY);
      this.logger.error(ERROR_LOGIN_EMPTY);
    } else {
      try {
        UserDto userDto = this.userService.getUserByUsername(username);
        if (userDto.getPassword().equals(DigestUtils.sha1Hex(password))) {
          session.setAttribute(USERNAME, userDto.getUsername());
          session.setAttribute(ROLE, userDto.getRole());
          session.setMaxInactiveInterval(MaxInactiveInterval);
          page = Config.getInstance().getValue(Config.PROFILE);
        }
      } catch (DaoException e) {
        request.getSession().setAttribute(ERROR, ERROR_LOGIN_WRONG);
        logger.error(ERROR_LOGIN_WRONG, e);
        page = Config.getInstance().getValue(Config.ERROR_PAGE);
      }
    }
    return page;
  }
}
