package com.katruk.web.controller.commands;

import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Unknown implements Command, PageAttribute {

  private final static String ERROR_LOGIN = "Unknown Command";
  private final Logger logger;

  public Unknown() {
    this.logger = Logger.getLogger(Unknown.class);
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute(ERROR, ERROR_LOGIN);
    this.logger.error(ERROR_LOGIN);
    return PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
  }
}
