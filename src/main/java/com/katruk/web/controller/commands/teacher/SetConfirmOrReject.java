package com.katruk.web.controller.commands.teacher;

import com.katruk.converter.EvaluationConverter;
import com.katruk.entity.Evaluation;
import com.katruk.entity.Period;
import com.katruk.service.EvaluationService;
import com.katruk.service.PeriodService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.service.impl.PeriodServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SetConfirmOrReject implements Command, PageAttribute {

  private final Logger logger;
  private final EvaluationService evaluationService;
  private final PeriodService periodService;

  public SetConfirmOrReject() {
    this.logger = Logger.getLogger(SetConfirmOrReject.class);
    this.evaluationService = new EvaluationServiceImpl();
    this.periodService = new PeriodServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.TEACHER_EVALUATIONS);
    try {
      System.out.println("status=" + request.getParameter(STATUS));
      Evaluation.Status status = Evaluation.Status.valueOf(request.getParameter(STATUS));
      Long evaluationId = Long.parseLong(request.getParameter(EVALUATION_ID));
      Evaluation evaluation = this.evaluationService.getEvaluationById(evaluationId);

      if (!status.equals(evaluation.getStatus())) {
        evaluation.setStatus(status);
        this.evaluationService.save(evaluation);
        logger.info(String.format("set status=%s for evaluation= %s", status, evaluation));
      }

      Collection<Evaluation> evaluations =
          this.evaluationService.getEvaluationBySubjects(evaluation.getSubject().getId());
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
//      page = PageConfig.getInstance().getValue(PageConfig.TEACHER_CONFIRMED);

    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable to show students which DECLARED ", e);
    }
    return page;
  }
}