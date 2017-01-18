package com.katruk.web.controller.commands.teacher;

import com.katruk.converter.EvaluationConverter;
import com.katruk.entity.Evaluation;
import com.katruk.entity.dto.EvaluationDto;
import com.katruk.exception.ServiceException;
import com.katruk.service.EvaluationService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Evaluate implements Command, PageAttribute {

  private final Logger logger;
  private final EvaluationService evaluationService;

  public Evaluate() {
    this.logger = Logger.getLogger(Evaluate.class);
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.EVALUATION);
    try {
      Evaluation.Rating rating = Evaluation.Rating.valueOf(request.getParameter(RATING));
      String feedback = request.getParameter(FEEDBACK);
      Long evaluationId = Long.parseLong(request.getParameter(EVALUATION_ID));
      Evaluation evaluation = this.evaluationService.getEvaluationById(evaluationId);
      evaluation.setRating(rating);
      evaluation.setFeedback(feedback);
      evaluation = this.evaluationService.save(evaluation);
      EvaluationDto evaluationDto = new EvaluationConverter().convertToDto(evaluation);
      request.setAttribute(EVALUATION, evaluationDto);
      logger.info(String.format("evaluation = %s", evaluationDto));
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all evaluations", e);
    }
    return page;
  }
}