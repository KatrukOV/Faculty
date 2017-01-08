package com.katruk.web.controller.commands;

import com.katruk.dao.DaoFactory;
import com.katruk.dao.utils.Config;
import com.katruk.domain.commands.ICommand;
import com.katruk.domain.entity.Discipline;
import com.katruk.domain.entity.Evaluation;
import com.katruk.domain.entity.human.Student;
import com.katruk.ui.PageAttribute;

import org.apache.log4j.Logger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectToDeclaredDisciplinesCommand implements ICommand, PageAttribute {

  private static final Logger logger = Logger.getLogger(RedirectToDeclaredDisciplinesCommand.class);
  private DaoFactory daoFactory = DaoFactory.getDAOFactory();
  private String page;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    try {

      HttpSession session = request.getSession();
//			String title = request.getParameter(TITLE);
      Student student = (Student) session.getAttribute(USER);
      List<Discipline>
          disciplineList =
          daoFactory.getEvaluationDAO().getDisciplinesByStatus(student,
                                                               Evaluation.StatusInDiscipline.DECLARED);

      if (disciplineList != null) {
        request.setAttribute("allDeclaredDisciplines", disciplineList);
        logger.info(String.format("show all declared Disciplines"));
      }
      page = Config.getInstance().getValue(Config.STUDENT_DECLARED_DISCIPLINES);
                        /*else throw new CommandException("Can't get any discipline");*/
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("show all declared Disciplines", e);
    }
//		request.setAttribute(PAGE, page);
    return page;
  }
}
