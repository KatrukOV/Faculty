package com.katruk.web.controller.commands;

import com.katruk.converter.SubjectConverter;
import com.katruk.entity.Subject;
import com.katruk.exception.ServiceException;
import com.katruk.service.SubjectService;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetSubjects implements Command, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;

  public GetSubjects() {
    this.logger = Logger.getLogger(GetSubjects.class);
    this.subjectService = new SubjectServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.SUBJECTS);
    try {
      Collection<Subject> subjects = this.subjectService.getAll();
      List subjectList = Collections.EMPTY_LIST;
      if (!subjects.isEmpty()) {
        subjectList = new SubjectConverter().convertToDto(subjects);
      }
      request.setAttribute(SUBJECT_LIST, subjectList);
      logger.info(String.format("get all subjects = %d", subjectList.size()));
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all subjects", e);
    }
    return page;
  }
}
