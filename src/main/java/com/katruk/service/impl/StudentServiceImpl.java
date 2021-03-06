package com.katruk.service.impl;

import com.katruk.dao.StudentDao;
import com.katruk.dao.mysql.StudentDaoMySql;
import com.katruk.entity.Student;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;

import org.apache.log4j.Logger;

import java.util.Collection;

public final class StudentServiceImpl implements StudentService {

  private final StudentDao studentDao;
  private final Logger logger;

  public StudentServiceImpl() {
    this.studentDao = new StudentDaoMySql();
    this.logger = Logger.getLogger(StudentServiceImpl.class);
  }

  @Override
  public Collection<Student> getAll() throws ServiceException {
    try {
      return this.studentDao.getAllStudent();
    } catch (DaoException e) {
      String message = "Cannot get all students.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Student getStudentById(final Long studentId) throws ServiceException {
    try {
      return this.studentDao.getStudentById(studentId);
    } catch (DaoException e) {
      String message = String.format("Cannot get student by id: %d.", studentId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public Student save(final Student student) throws ServiceException {
    try {
      return this.studentDao.save(student);
    } catch (DaoException e) {
      String message = "Cannot save student.";
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }

  @Override
  public void remove(final Long studentId) throws ServiceException {
    try {
      this.studentDao.delete(studentId);
    } catch (DaoException e) {
      String message = String.format("Cannot remove student by id: %d.", studentId);
      logger.error(message, e);
      throw new ServiceException(message, e);
    }
  }
}