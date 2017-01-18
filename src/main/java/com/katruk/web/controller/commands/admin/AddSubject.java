package com.katruk.web.controller.commands.admin;

import com.katruk.converter.TeacherConverter;
import com.katruk.entity.Teacher;
import com.katruk.exception.ServiceException;
import com.katruk.service.TeacherService;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AddSubject implements Command, PageAttribute {

  private final Logger logger;
  private final TeacherService teacherService;

  public AddSubject() {
    this.logger = Logger.getLogger(AddSubject.class);
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.ADD_SUBJECT);
    try {
      Collection<Teacher> teachers = this.teacherService.getAll();
      List teacherList = Collections.EMPTY_LIST;
      if (!teachers.isEmpty()) {
        teacherList = new TeacherConverter().convertToDto(teachers);
      }
      request.setAttribute(TEACHER_LIST, teacherList);
      logger.info(String.format("get all teachers = %d", teacherList.size()));
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all teachers", e);
    }
    return page;
  }
}