package com.katruk.web.controller.commands.teacher;

import com.katruk.converter.EvaluationConverter;
import com.katruk.entity.Evaluation;
import com.katruk.entity.dto.EvaluationDto;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ToEvaluation implements Command, PageAttribute {

  private final Logger logger;
  private final PeriodService periodService;
  private final SubjectService subjectService;
  private final StudentService studentService;
  private final EvaluationService evaluationService;

  public ToEvaluation() {
    this.logger = Logger.getLogger(ToEvaluation.class);
    this.periodService = new PeriodServiceImpl();
    this.subjectService = new SubjectServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.EVALUATION);
    try {
      Long evaluationId = Long.parseLong(request.getParameter(EVALUATION_ID));
      Evaluation evaluation = this.evaluationService.getEvaluationById(evaluationId);
      EvaluationDto evaluationDto = new EvaluationConverter().convertToDto(evaluation);
      request.setAttribute(EVALUATION, evaluationDto);
//      logger.info(String.format("evaluation = %d", evaluationDto));
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get evaluation", e);
    }
    return page;
  }
}