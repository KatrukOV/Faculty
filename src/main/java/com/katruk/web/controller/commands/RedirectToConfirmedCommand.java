package com.katruk.web.controller.commands;

import com.katruk.dao.DaoFactory;
import com.katruk.dao.utils.Config;
import com.katruk.domain.commands.ICommand;
import com.katruk.domain.entity.Discipline;
import com.katruk.domain.entity.Evaluation;
import com.katruk.domain.entity.human.Human;
import com.katruk.ui.PageAttribute;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToConfirmedCommand implements ICommand, PageAttribute {

  private static final Logger logger = Logger.getLogger(RedirectToConfirmedCommand.class);
  private String page;
  private DaoFactory daoFactory = DaoFactory.getDAOFactory();

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    try {
      System.out.println("conf begin");

      String title = (String) request.getParameter(TITLE);
      System.out.println("title=" + title);
      Discipline discipline = daoFactory.getDisciplineDAO().get(title);
//            List<Human> humanList = evaluationDAO.getStudentOfTeacher(teacher, Evaluation.StatusInDiscipline.DECLARED);
      System.out.println("dis===" + discipline);

      List<Evaluation> evaluationList = daoFactory.getEvaluationDAO().getByDisciplineAndStatus(
          discipline, Evaluation.StatusInDiscipline.DECLARED);

      List<Human> humanList = new ArrayList<>();
      if (evaluationList != null) {
        for (Evaluation evaluation : evaluationList) {
          humanList.add(evaluation.getStudent());
        }
      }
      request.setAttribute(TITLE, title);
      request.setAttribute(HUMAN_LIST, humanList);
      System.out.println(" conf ok");
      logger.info(String.format("show students which DECLARED discipline"));
      page = Config.getInstance().getValue(Config.TEACHER_CONFIRMED);

    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable to show students which DECLARED discipline", e);
    }
    return page;
  }
}
