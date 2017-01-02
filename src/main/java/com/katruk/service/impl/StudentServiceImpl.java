package com.katruk.service.impl;

import com.katruk.dao.StudentDao;
import com.katruk.dao.mysql.StudentDaoMySql;
import com.katruk.entity.Student;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;

public final class StudentServiceImpl implements StudentService {

  private final StudentDao studentDao;
  private final UserService userService;
  private final Logger logger;

  public StudentServiceImpl() {
    this.studentDao = new StudentDaoMySql();
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(StudentServiceImpl.class);
  }

  @Override
  public Student getStudentById(final Long studentId) throws ServiceException {
    final Student student;
    try {
      student = this.studentDao.getStudentById(studentId)
          .orElseThrow(() -> new DaoException("Student not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final User user = this.userService.getUserById(student.getUser().getId());
    student.setUser(user);
    return student;
  }
}
