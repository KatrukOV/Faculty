package com.katruk.web.controller.commands.teacher;

import com.katruk.converter.EvaluationConverter;
import com.katruk.entity.Evaluation;
import com.katruk.entity.Period;
import com.katruk.service.EvaluationService;
import com.katruk.service.PeriodService;
import com.katruk.service.StudentService;
import com.katruk.service.SubjectService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.service.impl.PeriodServiceImpl;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Evaluate implements Command, PageAttribute {

  private final Logger logger;
  private final PeriodService periodService;
  private final SubjectService subjectService;
  private final StudentService studentService;
  private final EvaluationService evaluationService;

  public Evaluate() {
    this.logger = Logger.getLogger(Evaluate.class);
    this.periodService = new PeriodServiceImpl();
    this.subjectService = new SubjectServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.TEACHER_EVALUATIONS);
    try {
      Long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
      System.out.println("subjectId" + subjectId);
      Collection<Evaluation> evaluations =
          this.evaluationService.getEvaluationBySubjects(subjectId);
      System.out.println("evaluations by SUBJECT_ID" + evaluations);
      //todo ????
      String title = "";
      List evaluationList = Collections.EMPTY_LIST;
      if (!evaluations.isEmpty()) {
        evaluationList = new EvaluationConverter().convertToDto(evaluations);
        title = evaluations.iterator().next().getSubject().getTitle();
      }
      request.setAttribute(TITLE, title);
      Period period = this.periodService.getLastPeriod();
      request.setAttribute(PERIOD_STATUS, period.getStatus());
      request.setAttribute(EVALUATION_LIST, evaluationList);
      logger.info(String.format("get all evaluations = %d", evaluationList.size()));
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all evaluations", e);
    }
    return page;
  }
}