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

public final class SetForm implements Command, PageAttribute {

  private final Logger logger;
  private final StudentService studentService;

  public SetForm() {
    this.logger = Logger.getLogger(SetForm.class);
    this.studentService = new StudentServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.STUDENTS);
    try {
      Student.Form form = Student.Form.valueOf(request.getParameter(FORM));
      System.out.println(">>>>>>>>>>>> form= " + form);
      System.out.println("MM="+STUDENT_ID);
      System.out.println("MM11="+request.getParameter(STUDENT_ID));
      Long studentId = Long.parseLong(request.getParameter(STUDENT_ID));
      System.out.println(">> studentId="+studentId);
      Student student = this.studentService.getStudentById(studentId);
      System.out.println(">>>>>>>>>>>> student= " + student);
      if (!form.equals(student.getForm())) {
        student.setForm(form);
        this.studentService.save(student);
        logger.info(String.format("set form=%s for student= %s", form, student));
      }
      Collection<Student> students = this.studentService.getAll();
      List studentList = Collections.EMPTY_LIST;
      if (!students.isEmpty()) {
        studentList = new StudentConverter().convertToDto(students);
      }
      request.setAttribute(STUDENT_LIST, studentList);
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable set contract for student", e);
    }
    return page;
  }
}
