package com.katruk.web.controller.commands;

import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UnknownCommand implements ICommand, PageAttribute {

  private final static String ERROR_LOGIN = "Unknown Command";
  private final Logger logger;

  public UnknownCommand() {
    this.logger = Logger.getLogger(UnknownCommand.class);
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    System.out.println(">>> UnknownCommand execute");
    request.setAttribute(ERROR, ERROR_LOGIN);
    this.logger.error(ERROR_LOGIN);
    return Config.getInstance().getValue(Config.ERROR_PAGE);
  }
}
