package com.katruk.web.controller.commands.admin;

import com.katruk.converter.StudentConverter;
import com.katruk.entity.Student;
import com.katruk.exception.ServiceException;
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

public final class SetContract implements Command, PageAttribute {

  private final Logger logger;
  private final StudentService studentService;

  public SetContract() {
    this.logger = Logger.getLogger(SetContract.class);
    this.studentService = new StudentServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.STUDENTS);
    try {
      Student.Contract contract = Student.Contract.valueOf(request.getParameter(CONTRACT));
      Long studentId = Long.parseLong(request.getParameter(STUDENT_ID));
      Student student = this.studentService.getStudentById(studentId);
      if (!contract.equals(student.getContract())) {
        student.setContract(contract);
        this.studentService.save(student);
        logger.info(String.format("set contract=%s for student= %s", contract, student));
      }
      Collection<Student> students = this.studentService.getAll();
      List studentList = Collections.EMPTY_LIST;
      if (!students.isEmpty()) {
        studentList = new StudentConverter().convertToDto(students);
      }
      request.setAttribute(STUDENT_LIST, studentList);
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable set contract for student", e);
    }
    return page;
  }
}
