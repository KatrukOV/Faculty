package com.katruk.web.controller.commands;

import com.katruk.entity.User;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class ToProfile implements Command, PageAttribute {

  private final Logger logger;

  public ToProfile() {
    this.logger = Logger.getLogger(ToProfile.class);
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.PROFILE);
    HttpSession session = request.getSession();
    User.Role role = (User.Role) session.getAttribute(ROLE);
    if (role.equals(User.Role.ADMIN)) {
      page = Config.getInstance().getValue(Config.ADMIN_PROFILE);
    }
    logger.info("go to :" + page);
    return page;
  }
}
