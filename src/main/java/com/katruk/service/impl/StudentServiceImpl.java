package com.katruk.service.impl;

import com.katruk.dao.StudentDao;
import com.katruk.dao.mysql.StudentDaoMySql;
import com.katruk.entity.Student;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;

import org.apache.log4j.Logger;

public class StudentServiceImpl implements StudentService {

  private final StudentDao studentDao;
  private final Logger logger;

  public StudentServiceImpl() {
    this.studentDao = new StudentDaoMySql();
    this.logger = Logger.getLogger(StudentServiceImpl.class);
  }

  @Override
  public Student getStudentById(Long studentId) throws ServiceException {
    Student student;
    try {
      student = this.studentDao.getStudentById(studentId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return student;
  }
}
