package com.katruk.service.impl;

import com.katruk.dao.TeacherDao;
import com.katruk.dao.mysql.TeacherDaoMySql;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.TeacherService;

import org.apache.log4j.Logger;

import java.util.Collection;

public final class TeacherServiceImpl implements TeacherService {

  private final TeacherDao teacherDao;
  private final Logger logger;

  public TeacherServiceImpl() {
    this.teacherDao = new TeacherDaoMySql();
    this.logger = Logger.getLogger(TeacherServiceImpl.class);
  }

  @Override
  public Collection<Teacher> getAll() throws ServiceException {
    try {
      return this.teacherDao.getAllTeacher();
    } catch (DaoException e) {
      logger.error("Cannot get all teachers.", e);
      throw new ServiceException("Cannot get all teachers.", e);
    }
  }

  @Override
  public Teacher getTeacherById(final Long teacherId) throws ServiceException {
    try {
      return this.teacherDao.getTeacherById(teacherId);
    } catch (DaoException e) {
      logger.error(String.format("Cannot get teacher by id: %d.", teacherId), e);
      throw new ServiceException(String.format("Cannot get teacher by id: %d.", teacherId), e);
    }
  }

  @Override
  public Teacher save(final Teacher teacher) throws ServiceException {
    try {
      return this.teacherDao.save(teacher);
    } catch (DaoException e) {
      logger.error("Cannot save teacher.", e);
      throw new ServiceException("Cannot save teacher.", e);
    }
  }

  @Override
  public void remove(final Long teacherId) throws ServiceException {
    try {
      this.teacherDao.delete(teacherId);
    } catch (DaoException e) {
      logger.error(String.format("Cannot remove teacher with id: %d.", teacherId), e);
      throw new ServiceException(String.format("Cannot remove teacher with id: %d.", teacherId), e);
    }
  }
}
