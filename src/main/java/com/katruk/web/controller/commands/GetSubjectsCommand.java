package com.katruk.web.controller.commands;

import com.katruk.service.SubjectService;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetSubjectsCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;

  public GetSubjectsCommand() {
    this.logger = Logger.getLogger(GetSubjectsCommand.class);
    this.subjectService = null;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page;
    try {
      List<Discipline> disciplineList = daoFactory.getDisciplineDAO().getAll();
      request.setAttribute(DISCIPLINE_LIST, disciplineList);
      page = Config.getInstance().getValue(Config.DISCIPLINES);
      logger.info(String.format("get all disciplines = %d", disciplineList.size()));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all disciplines", e);
    }
    return page;
  }
}
