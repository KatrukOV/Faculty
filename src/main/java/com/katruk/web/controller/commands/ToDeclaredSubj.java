package com.katruk.web.controller.commands;

import com.katruk.entity.Student;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ToDeclaredSubj implements Command, PageAttribute {

  private final Logger logger;

  public ToDeclaredSubj() {
    this.logger = Logger.getLogger(ToDeclaredSubj.class);
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ERROR_PAGE);
    try {
      HttpSession session = request.getSession();
//			String title = request.getParameter(TITLE);
//      Student student = (Student) session.getAttribute(USER);
//      List<Discipline>
//          disciplineList =
//          daoFactory.getEvaluationDAO().getDisciplinesByStatus(student,
//                                                               Evaluation.StatusInDiscipline.DECLARED);

//      if (disciplineList != null) {
//        request.setAttribute("allDeclaredDisciplines", disciplineList);
//        logger.info(String.format("show all declared Disciplines"));
//      }
//      page = Config.getInstance().getValue(Config.STUDENT_DECLARED_DISCIPLINES);
                        /*else throw new CommandException("Can't get any discipline");*/
    } catch (Exception e) {
//      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("show all declared Disciplines", e);
    }
//		request.setAttribute(PAGE, page);
    return page;
  }
}
