package com.katruk.service.impl;

import com.katruk.dao.SubjectDao;
import com.katruk.dao.mysql.SubjectDaoMySql;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

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
      logger.error("Cannot get all subjects.", e);
      throw new ServiceException("Cannot get all subjects.", e);
    }
  }

  @Override
  public Subject getSubjectById(final Long subjectId) throws ServiceException {
    try {
      return this.subjectDao.getSubjectById(subjectId);
    } catch (DaoException e) {
      logger.error(String.format("Cannot get subject by id: %d.", subjectId), e);
      throw new ServiceException(String.format("Cannot get subject by id: %d.", subjectId), e);
    }
  }

  @Override
  public Collection<Subject> getSubjectByTeacher(final Teacher teacher) throws ServiceException {
    try {
      return this.subjectDao.getSubjectByTeacher(teacher.getId());
    } catch (DaoException e) {
      String mess = String.format("Cannot get subjects by teacher with id: %d.", teacher.getId());
      logger.error(mess, e);
      throw new ServiceException(mess, e);
    }
  }

  @Override
  public Subject save(final Subject subject) throws ServiceException {
    try {
      return this.subjectDao.save(subject);
    } catch (DaoException e) {
      logger.error("Cannot save subject.", e);
      throw new ServiceException("Cannot save subject.", e);
    }
  }

  @Override
  public void remove(final Long subjectId) throws ServiceException {
    try {
      this.subjectDao.delete(subjectId);
    } catch (DaoException e) {
      logger.error(String.format("Cannot remove subject with id: %d.", subjectId), e);
      throw new ServiceException(String.format("Cannot remove subject with id: %d.", subjectId), e);
    }
  }
}