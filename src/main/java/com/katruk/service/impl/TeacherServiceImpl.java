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
      String message = "Cannot get all teachers.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Teacher getTeacherById(final Long teacherId) throws ServiceException {
    try {
      return this.teacherDao.getTeacherById(teacherId);
    } catch (DaoException e) {
      String message = String.format("Cannot get teacher by id: %d.", teacherId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Teacher save(final Teacher teacher) throws ServiceException {
    try {
      return this.teacherDao.save(teacher);
    } catch (DaoException e) {
      String message = "Cannot save teacher.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public void remove(final Long teacherId) throws ServiceException {
    try {
      this.teacherDao.delete(teacherId);
    } catch (DaoException e) {
      String message = String.format("Cannot remove teacher with id: %d.", teacherId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }
}