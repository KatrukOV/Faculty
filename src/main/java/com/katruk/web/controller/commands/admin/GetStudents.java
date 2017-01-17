package com.katruk.web.controller.commands.admin;

import com.katruk.converter.StudentConverter;
import com.katruk.entity.Student;
import com.katruk.service.StudentService;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetStudents implements Command, PageAttribute {

  private final Logger logger;
  private final StudentService studentService;

  public GetStudents() {
    this.logger = Logger.getLogger(GetStudents.class);
    this.studentService = new StudentServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.STUDENTS);
    try {
      Collection<Student> students = this.studentService.getAll();
      List studentList = Collections.EMPTY_LIST;
      if (!students.isEmpty()) {
        studentList = new StudentConverter().convertToDto(students);
      }
      request.setAttribute(STUDENT_LIST, studentList);
      logger.info(String.format("get all students = %d", studentList.size()));
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all students", e);
    }
    return page;
  }
}
