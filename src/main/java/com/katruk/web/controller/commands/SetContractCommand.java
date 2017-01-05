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

public class SetContractCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final StudentService studentService;

  public SetContractCommand() {
    this.logger = Logger.getLogger(SetContractCommand.class);
    this.studentService = new StudentServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ALL_STUDENTS);
    try {
      Student.Contract contract = Student.Contract.valueOf(request.getParameter(CONTRACT));
      System.out.println(">>>>>>>>>>>> contract= " + contract);
      Long studentId = Long.parseLong(request.getParameter(STUDENT_ID));
      Student student = this.studentService.getStudentById(studentId);
      System.out.println(">>>>>>>>>>>> student= " + student);
      student.setContract(contract);
      this.studentService.save(student);
      Collection<Student> students = this.studentService.getAll();
      List<StudentDto> studentList = new StudentConverter().convertToDto(students);
      request.setAttribute(STUDENT_LIST, studentList);
      logger.info(String.format("set contract=%s for student= %s", contract, student));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable set contract for student", e);
    }
    return page;
  }
}
