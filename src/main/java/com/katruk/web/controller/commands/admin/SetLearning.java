package com.katruk.web.controller.commands.admin;

import com.katruk.entity.Period;
import com.katruk.exception.ServiceException;
import com.katruk.service.PeriodService;
import com.katruk.service.impl.PeriodServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SetLearning implements Command, PageAttribute {

  private final Logger logger;
  private final PeriodService periodService;

  public SetLearning() {
    this.logger = Logger.getLogger(SetLearning.class);
    this.periodService = new PeriodServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ADMIN_PROFILE);
    Period period;
    try {
      period = this.periodService.getLastPeriod();
    } catch (ServiceException e) {
      logger.info("create new Period", e);
      period = new Period();
    }
    period.setStatus(Period.Status.LEARNING);
    try {
      period = this.periodService.save(period);
      request.setAttribute(PERIOD_STATUS, period.getStatus().name());
      request.setAttribute(PERIOD_DATE, period.getDate());
    } catch (ServiceException e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable set period LEARNING", e);
    }
    return page;
  }
}
