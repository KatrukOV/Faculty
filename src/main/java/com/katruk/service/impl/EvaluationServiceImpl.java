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
import java.util.NoSuchElementException;
import java.util.Objects;

public final class EvaluationServiceImpl implements EvaluationService {

  private final Logger logger;
  private final StudentService studentService;
  private final SubjectService subjectService;
  private final EvaluationDao evaluationDao;

  public EvaluationServiceImpl() {
    this.logger = Logger.getLogger(EvaluationServiceImpl.class);
    this.studentService = new StudentServiceImpl();
    this.subjectService = new SubjectServiceImpl();
    this.evaluationDao = new EvaluationDaoMySql();
  }

  @Override
  public Evaluation getEvaluationById(Long evaluationId) throws ServiceException {
    final Evaluation evaluation;
    try {
      evaluation = this.evaluationDao.getEvaluationById(evaluationId)
          .orElseThrow(
              () -> new DaoException("Evaluation not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error(String.format("Cannot get evaluation by id: %d.", evaluationId), e);
      throw new ServiceException(
          String.format("Cannot get evaluation by id: %d.", evaluationId), e);
    }
    final Subject subject = this.subjectService.getSubjectById(evaluation.getSubject().getId());
    evaluation.setSubject(subject);
    final Student student = this.studentService.getStudentById(evaluation.getStudent().getId());
    evaluation.setStudent(student);
    return evaluation;
  }

  @Override
  public Collection<Evaluation> getEvaluationBySubjects(final Long subjectId)
      throws ServiceException {
    Collection<Evaluation> evaluations;
    try {
      evaluations = this.evaluationDao.getEvaluationBySubject(subjectId);
    } catch (DaoException e) {
      logger.error(String.format("Cannot get evaluations by subject with id: %d.", subjectId), e);
      throw new ServiceException(
          String.format("Cannot get evaluations by subject with id: %d.", subjectId), e);
    }
    if (!evaluations.isEmpty()) {
      Subject subject = this.subjectService.getSubjectById(subjectId);
      Collection<Student> students = this.studentService.getAll();
      for (Evaluation evaluation : evaluations) {
        students.stream()
            .filter(student -> Objects.equals(student.getId(), evaluation.getStudent().getId()))
            .forEach(student -> {
              evaluation.setStudent(student);
              evaluation.setSubject(subject);
            });
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
      logger.error(String.format("Cannot get evaluations by student with id: %d.", studentId), e);
      throw new ServiceException(
          String.format("Cannot get evaluations by student with id: %d.", studentId), e);
    }
    if (!evaluations.isEmpty()) {
      Collection<Subject> subjects = this.subjectService.getAll();
      Student student = this.studentService.getStudentById(studentId);
      for (Evaluation evaluation : evaluations) {
        subjects.stream()
            .filter(subject -> Objects.equals(evaluation.getSubject().getId(), subject.getId()))
            .forEach(subject -> {
              evaluation.setSubject(subject);
              evaluation.setStudent(student);
            });
      }
    }
    return evaluations;
  }

  @Override
  public Evaluation save(final Evaluation evaluation) throws ServiceException {
    try {
      this.evaluationDao.save(evaluation);
    } catch (DaoException e) {
      logger.error("Cannot save evaluation.", e);
      throw new ServiceException("Cannot save evaluation.", e);
    }
    return evaluation;
  }
}