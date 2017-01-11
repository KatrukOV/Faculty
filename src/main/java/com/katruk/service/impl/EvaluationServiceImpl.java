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
  public Collection<Evaluation> getEvaluationByTeacherAndSubjects(Teacher teacher,
                                                                  Subject subject)
      throws ServiceException {
    return null;
  }

  @Override
  public Collection<Evaluation> getEvaluationByStudentAndSubjects(Student student,
                                                                  Subject subject)
      throws ServiceException {
    return null;
  }

  @Override
  public Evaluation save(Evaluation evaluation) throws ServiceException {
    try {
      this.evaluationDao.save(evaluation);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return evaluation;
  }
}

/*

  @Override
  public Collection<Subject> getAll() throws ServiceException {
    Collection<Subject> result;
    try {
      result = this.subjectDao.getAllSubject();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }

  @Override
  public Subject getSubjectById(final Long subjectId) throws ServiceException {
    final Subject subject;
    try {
      subject = this.subjectDao.getSubjectById(subjectId)
          .orElseThrow(() -> new DaoException("Subject not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final Teacher teacher = this.teacherService.getTeacherById(subject.getTeacher().getId());
    subject.setTeacher(teacher);
    return subject;
  }

  @Override
  public Collection<Subject> getSubjectByTeacher(final Teacher teacher) throws ServiceException {
    Collection<Subject> result;
    try {
      result = this.subjectDao.getSubjectByTeacher(teacher.getId());
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }

  @Override
  public Collection<Subject> getSubjectsByStudent(final Student student) throws ServiceException {
    Collection<Subject> result;
    try {
      result = this.subjectDao.getSubjectsByStudent(student.getId());
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }

  @Override
  public Subject save(Subject subject) throws ServiceException {
    try {
      this.subjectDao.save(subject);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return subject;
  }

  @Override
  public void remove(Long subjectId) throws ServiceException {
    try {
      this.subjectDao.delete(subjectId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
  }
 */