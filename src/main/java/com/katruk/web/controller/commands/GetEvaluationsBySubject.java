package com.katruk.web.controller.commands;

import com.katruk.converter.EvaluationConverter;
import com.katruk.entity.Evaluation;
import com.katruk.service.EvaluationService;
import com.katruk.service.StudentService;
import com.katruk.service.SubjectService;
import com.katruk.service.impl.EvaluationServiceImpl;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetEvaluationsBySubject implements Command, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;
  private final StudentService studentService;
  private final EvaluationService evaluationService;

  public GetEvaluationsBySubject() {
    this.logger = Logger.getLogger(GetEvaluationsBySubject.class);
    this.subjectService = new SubjectServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.evaluationService = new EvaluationServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.TEACHER_EVALUATIONS);
    try {
      Long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
      Collection<Evaluation> evaluations =
          this.evaluationService.getEvaluationBySubjects(subjectId);
      List evaluationList = Collections.EMPTY_LIST;
      if (!evaluations.isEmpty()) {
        evaluationList = new EvaluationConverter().convertToDto(evaluations);
      }
      request.setAttribute(EVALUATION_LIST, evaluationList);
      logger.info(String.format("get all evaluations = %d", evaluationList.size()));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable get all evaluations", e);
    }
    return page;
  }
}