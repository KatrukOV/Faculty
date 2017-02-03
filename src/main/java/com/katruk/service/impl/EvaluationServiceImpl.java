package com.katruk.service.impl;

import com.katruk.dao.EvaluationDao;
import com.katruk.dao.mysql.EvaluationDaoMySql;
import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.EvaluationService;
import com.katruk.service.StudentService;
import com.katruk.service.SubjectService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Objects;

public final class EvaluationServiceImpl implements EvaluationService {

  private final Logger logger;
  private final SubjectService subjectService;
  private final EvaluationDao evaluationDao;

  public EvaluationServiceImpl() {
    this.logger = Logger.getLogger(EvaluationServiceImpl.class);
    this.subjectService = new SubjectServiceImpl();
    this.evaluationDao = new EvaluationDaoMySql();
  }

  @Override
  public Evaluation getEvaluationById(Long evaluationId) throws ServiceException {
    final Evaluation evaluation;
    try {
      evaluation = this.evaluationDao.getEvaluationById(evaluationId);
    } catch (DaoException e) {
      String message = String.format("Cannot get evaluation by id: %d.", evaluationId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
    final Subject subject = this.subjectService.getSubjectById(evaluation.getSubject().getId());
    evaluation.setSubject(subject);
    return evaluation;
  }

  @Override
  public Collection<Evaluation> getEvaluationBySubjects(final Long subjectId)
      throws ServiceException {
    Collection<Evaluation> evaluations;
    try {
      evaluations = this.evaluationDao.getEvaluationBySubject(subjectId);
    } catch (DaoException e) {
      String message = String.format("Cannot get evaluations by subject with id: %d.", subjectId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
    if (!evaluations.isEmpty()) {
      Subject subject = this.subjectService.getSubjectById(subjectId);
      for (Evaluation evaluation : evaluations) {
        evaluation.setSubject(subject);
      }
    }
    return evaluations;
  }

  @Override
  public Collection<Evaluation> getEvaluationByStudent(final Long studentId)
      throws ServiceException {
    Collection<Evaluation> evaluations;
    try {
      evaluations = this.evaluationDao.getEvaluationByStudent(studentId);
    } catch (DaoException e) {
      String message = String.format("Cannot get evaluations by student with id: %d.", studentId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
    if (!evaluations.isEmpty()) {
      Subject subject = this.subjectService.getSubjectById(
          evaluations.iterator().next().getSubject().getId()
      );
      for (Evaluation evaluation : evaluations) {
        evaluation.setSubject(subject);
      }
    }
    return evaluations;
  }

  @Override
  public Evaluation save(final Evaluation evaluation) throws ServiceException {
    try {
      return this.evaluationDao.save(evaluation);
    } catch (DaoException e) {
      String message = "Cannot save evaluation.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }
}