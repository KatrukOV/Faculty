package com.katruk.web.controller.commands.admin;

import com.katruk.entity.Period;
import com.katruk.service.PeriodService;
import com.katruk.service.impl.PeriodServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SetDistribution implements Command, PageAttribute {

  private final Logger logger;
  private final PeriodService periodService;

  public SetDistribution() {
    this.logger = Logger.getLogger(SetDistribution.class);
    this.periodService = new PeriodServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {

    String page = Config.getInstance().getValue(Config.ADMIN_PROFILE);
    try {
      Period period = this.periodService.getLastPeriod();
      period.setStatus(Period.Status.DISTRIBUTION);
      period = this.periodService.save(period);

      request.setAttribute(PERIOD_STATUS, period.getStatus().name());
      request.setAttribute(PERIOD_DATE, period.getDate());


      /*
      Student.Form form = Student.Form.valueOf(request.getParameter(FORM));

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
      */
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable set period DISTRIBUTION", e);
    }
    return page;
  }
}
