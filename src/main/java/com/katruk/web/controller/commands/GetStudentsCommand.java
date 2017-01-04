package com.katruk.web.controller.commands;

import com.katruk.converter.StudentConverter;
import com.katruk.entity.Student;
import com.katruk.entity.dto.StudentDto;
import com.katruk.service.StudentService;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetStudentsCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final StudentService studentService;

  public GetStudentsCommand() {
    this.logger = Logger.getLogger(GetStudentsCommand.class);
    this.studentService = new StudentServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = request.getContextPath();
    try {
      Collection<Student> students = this.studentService.getAll();
      List<StudentDto> studentList = new StudentConverter().convertToDto(students);
      request.setAttribute(STUDENT_LIST, studentList);
//      page = Config.getInstance().getValue(Config.ALL_STUDENTS);
      logger.info(String.format("get all students = %d", studentList.size()));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all students", e);
    }
    return page;
  }
}
