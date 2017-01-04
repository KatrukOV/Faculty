package com.katruk.web.controller.commands;

import com.katruk.converter.SubjectConverter;
import com.katruk.entity.Subject;
import com.katruk.entity.dto.SubjectDto;
import com.katruk.service.SubjectService;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetSubjectsCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;

  public GetSubjectsCommand() {
    this.logger = Logger.getLogger(GetSubjectsCommand.class);
    this.subjectService = null;
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ALL_SUBJECTS);
    try {
      Collection<Subject> subjects = this.subjectService.getAll();
      List<SubjectDto> subjectList = new SubjectConverter().convertToDto(subjects);
      request.setAttribute(SUBJECT_LIST, subjectList);
      logger.info(String.format("get all subjects = %d", subjectList.size()));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all disciplines", e);
    }
    return page;
  }
}
