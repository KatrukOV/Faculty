package com.katruk.service.impl;

import com.katruk.dao.SubjectDao;
import com.katruk.dao.mysql.SubjectDaoMySql;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.SubjectService;

import org.apache.log4j.Logger;

import java.util.Collection;

public final class SubjectServiceImpl implements SubjectService {

  private final SubjectDao subjectDao;
  private final Logger logger;

  public SubjectServiceImpl() {
    this.subjectDao = new SubjectDaoMySql();
    this.logger = Logger.getLogger(SubjectServiceImpl.class);
  }

  @Override
  public Collection<Subject> getAll() throws ServiceException {
    try {
      return this.subjectDao.getAllSubject();
    } catch (DaoException e) {
      String message = "Cannot get all subjects.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Subject getSubjectById(final Long subjectId) throws ServiceException {
    try {
      return this.subjectDao.getSubjectById(subjectId);
    } catch (DaoException e) {
      String message = String.format("Cannot get subject by id: %d.", subjectId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Collection<Subject> getSubjectByTeacher(final Teacher teacher) throws ServiceException {
    try {
      return this.subjectDao.getSubjectByTeacher(teacher.getId());
    } catch (DaoException e) {
      String message = String.format("Can't get subjects by teacher with id: %d.", teacher.getId());
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Subject save(final Subject subject) throws ServiceException {
    try {
      return this.subjectDao.save(subject);
    } catch (DaoException e) {
      String message = "Cannot save subject.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public void remove(final Long subjectId) throws ServiceException {
    try {
      this.subjectDao.delete(subjectId);
    } catch (DaoException e) {
      String message = String.format("Cannot remove subject with id: %d.", subjectId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }
}