package com.katruk.web.controller.commands;

import com.katruk.converter.TeacherConverter;
import com.katruk.entity.Teacher;
import com.katruk.entity.dto.TeacherDto;
import com.katruk.exception.ServiceException;
import com.katruk.service.TeacherService;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetTeachersCommand implements ICommand, PageAttribute {

  private final TeacherService teacherService;
  private final Logger logger;

  public GetTeachersCommand() {
    this.teacherService = new TeacherServiceImpl();
    this.logger = Logger.getLogger(GetTeachersCommand.class);
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = request.getContextPath();
    try {
      Collection<Teacher> teachers = this.teacherService.gatAll();
      List<TeacherDto> teacherList = new TeacherConverter().convertToDto(teachers);
      request.setAttribute(TEACHER_LIST, teacherList);
      logger.info(String.format("get all teachers = %d", teacherList.size()));
    } catch (ServiceException e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all students", e);
    }
    return page;
  }
}
