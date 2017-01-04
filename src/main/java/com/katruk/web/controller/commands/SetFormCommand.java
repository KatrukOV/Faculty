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

public class SetFormCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final StudentService studentService;

  public SetFormCommand() {
    this.logger = Logger.getLogger(SetFormCommand.class);
    this.studentService = new StudentServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ALL_STUDENTS);
    try {
      Student.Form form = Student.Form.valueOf(request.getParameter(FORM));
      System.out.println(">>>>>>>>>>>> form= " + form);
      Long studentId = Long.parseLong(request.getParameter(STUDENT_ID));
      Student student = this.studentService.getStudentById(studentId);
      System.out.println(">>>>>>>>>>>> student= " + student);
      student.setForm(form);
      this.studentService.update(student);
      Collection<Student> students = this.studentService.getAll();
      List<StudentDto> studentList = new StudentConverter().convertToDto(students);
      request.setAttribute(STUDENT_LIST, studentList);
      logger.info(String.format("set form=%s for student= %s", form, student));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable set contract for student", e);
    }
    return page;
  }
}
