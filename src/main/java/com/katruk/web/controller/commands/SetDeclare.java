package com.katruk.web.controller.commands;

import com.katruk.converter.SubjectConverter;
import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.User;
import com.katruk.exception.ServiceException;
import com.katruk.service.EvaluationService;
import com.katruk.service.StudentService;
import com.katruk.service.SubjectService;
import com.katruk.service.UserService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SetDeclare implements Command, PageAttribute {

  private final Logger logger;
  private final UserService userService;
  private final StudentService studentService;
  private final SubjectService subjectService;
  private final EvaluationService evaluationService;

  public SetDeclare() {
    this.logger = Logger.getLogger(SetDeclare.class);
    this.userService = new UserServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.subjectService = new SubjectServiceImpl();
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.SUBJECTS);
    try {
      String username = (String) request.getSession().getAttribute(USERNAME);
      User user = this.userService.getUserByUsername(username);
      Student student = this.studentService.getStudentById(user.getId());
      Long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
      Subject subject = this.subjectService.getSubjectById(subjectId);
      Evaluation evaluation = new Evaluation(subject, student, status, rating, feedback);
      evaluation.setStudent(student);
      evaluation.setSubject(subject);
      evaluation.setStatus(Evaluation.Status.DECLARED);
      this.evaluationService.save(evaluation);
      Collection<Subject> subjects = this.subjectService.getAll();
      List subjectList = Collections.EMPTY_LIST;
      if (!subjects.isEmpty()) {
        subjectList = new SubjectConverter().convertToDto(subjects);
      }
      request.setAttribute(SUBJECT_LIST, subjectList);
      logger.info(String.format("get all subjects = %d", subjectList.size()));
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("SetDeclare ", e);
    }
    return page;
  }
}
