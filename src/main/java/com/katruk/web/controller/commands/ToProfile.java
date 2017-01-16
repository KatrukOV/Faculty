package com.katruk.web.controller.commands;

import com.katruk.entity.Period;
import com.katruk.entity.User;
import com.katruk.exception.ServiceException;
import com.katruk.service.PeriodService;
import com.katruk.service.impl.PeriodServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class ToProfile implements Command, PageAttribute {

  private final Logger logger;
  private final PeriodService periodService;

  public ToProfile() {
    this.logger = Logger.getLogger(ToProfile.class);
    this.periodService = new PeriodServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.PROFILE);
    HttpSession session = request.getSession();
    User.Role role = (User.Role) session.getAttribute(ROLE);
    if (role.equals(User.Role.ADMIN)) {
      page = Config.getInstance().getValue(Config.ADMIN_PROFILE);
      Period period;
      try {
        period = this.periodService.getLastPeriod();

        System.out.println("p +"+period);

        request.setAttribute(PERIOD_STATUS, period.getStatus().name());
        request.setAttribute(PERIOD_DATE, period.getDate());
      } catch (ServiceException e) {
        page = Config.getInstance().getValue(Config.ERROR_PAGE);
        logger.error("Unable get period", e);
      }
    }
    logger.info("go to :" + page);
    return page;
  }
}
