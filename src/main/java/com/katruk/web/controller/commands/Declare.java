package com.katruk.web.controller.commands;

import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.User;
import com.katruk.service.EvaluationService;
import com.katruk.service.StudentService;
import com.katruk.service.SubjectService;
import com.katruk.service.UserService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Declare implements Command, PageAttribute {

  private final Logger logger;
  private final UserService userService;
  private final StudentService studentService;
  private final SubjectService subjectService;
  private final EvaluationService evaluationService;

  public Declare() {
    this.logger = Logger.getLogger(Declare.class);
    this.userService = new UserServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.subjectService = new SubjectServiceImpl();
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.SUBJECTS);
    try {
      String username = request.getParameter(USERNAME);
      User user = this.userService.getUserByUsername(username);
      Student student = this.studentService.getStudentById(user.getId());

      Long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
      Subject subject = this.subjectService.getSubjectById(subjectId);
      Evaluation evaluation = new Evaluation();
      evaluation.setSubject(subject);
      evaluation.setStudent(student);
      evaluation.setStatus(Evaluation.Status.DECLARED);

       this.evaluationService.save(evaluation);


//      HttpSession session = request.getSession();
//			String title = request.getParameter(TITLE);
//      Student student = (Student) session.getAttribute(USER);
//      List<Discipline>
//          disciplineList =
//          daoFactory.getEvaluationDAO().getDisciplinesByStatus(student,
//                                                               Evaluation.StatusInDiscipline.DECLARED);
//
//      if (disciplineList != null) {
//        request.setAttribute("allDeclaredDisciplines", disciplineList);
//        logger.info(String.format("show all declared Disciplines"));
//      }
//      page = Config.getInstance().getValue(Config.STUDENT_DECLARED_DISCIPLINES);
                        /*else throw new CommandException("Can't get any discipline");*/

    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("show all declared Disciplines", e);
    }
    return page;
  }
}
