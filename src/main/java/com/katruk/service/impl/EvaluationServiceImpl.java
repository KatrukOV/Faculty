package com.katruk.service.impl;

import com.katruk.dao.EvaluationDao;
import com.katruk.dao.SubjectDao;
import com.katruk.dao.mysql.EvaluationDaoMySql;
import com.katruk.dao.mysql.SubjectDaoMySql;
import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.EvaluationService;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;

public final class EvaluationServiceImpl implements EvaluationService {

  private final Logger logger;
  private final EvaluationDao evaluationDao;

  public EvaluationServiceImpl() {
    this.logger = Logger.getLogger(EvaluationServiceImpl.class);
    this.evaluationDao = new EvaluationDaoMySql();

  }

  @Override
  public Collection<Evaluation> getEvaluationBySubjects(final Long subjectId)
      throws ServiceException {
    Collection<Evaluation> result;
    try {
      result = this.evaluationDao.getEvaluationBySubject(subjectId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }

  @Override
  public Collection<Evaluation> getEvaluationByStudent(final Long studentId)
      throws ServiceException {
    Collection<Evaluation> result;
    try {
      result = this.evaluationDao.getEvaluationByStudent(studentId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }

  @Override
  public Evaluation save(final Evaluation evaluation) throws ServiceException {
    try {
      this.evaluationDao.save(evaluation);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return evaluation;
  }
}