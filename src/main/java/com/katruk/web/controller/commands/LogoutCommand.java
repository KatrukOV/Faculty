package com.katruk.web.controller.commands;

import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class LogoutCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final static String LOGOUT = "User was logout";

  public LogoutCommand() {
    this.logger = Logger.getLogger(LogoutCommand.class);
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    request.getSession().removeAttribute(USERNAME);
    request.getSession().invalidate();
    request.setAttribute(INFO, LOGOUT);
    logger.info(LOGOUT);
    return Config.getInstance().getValue(Config.INDEX);
  }
}
