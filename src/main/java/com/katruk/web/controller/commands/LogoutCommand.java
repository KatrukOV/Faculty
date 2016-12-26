package com.katruk.web.controller.commands;

import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements ICommand, PageAttribute {

  private final String logout = "User was logout";

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
//    request.getSession().removeAttribute(USERNAME);
    request.getSession().invalidate();
    request.setAttribute("info", logout);
    return Config.getInstance().getValue(Config.INDEX);
  }
}
