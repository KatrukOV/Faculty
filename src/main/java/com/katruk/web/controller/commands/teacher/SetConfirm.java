package com.katruk.web.controller.commands.teacher;

import com.katruk.service.EvaluationService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetConfirm implements Command, PageAttribute {

  private final Logger logger;
  private final EvaluationService evaluationService;

  public SetConfirm() {
    this.logger = Logger.getLogger(SetConfirm.class);
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page;
    try {
      System.out.println("conf begin");
      String title = request.getParameter(TITLE);
      System.out.println("title=" + title);
      request.setAttribute(TITLE, title);
      System.out.println(" conf ok");








      logger.info(String.format("show students which DECLARED discipline"));
      page = Config.getInstance().getValue(Config.TEACHER_CONFIRMED);

    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable to show students which DECLARED discipline", e);
    }
    return page;
  }
}