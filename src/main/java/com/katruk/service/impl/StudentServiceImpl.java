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

import java.util.Collection;
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
  public Collection<Student> getAll() throws ServiceException {
    final Collection<Student> students;
    try {
      students = this.studentDao.getAllStudent();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    Collection<User> users = this.userService.getAll();
    // TODO: 04.01.17 add to students real user (use stream())
    for (User user : users) {
      for (Student student : students) {
        if (user.getId() == student.getId()){
          student.setUser(user);
        }
      }
    }
    return students;
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

  @Override
  public Student create(final Student student) throws ServiceException {
    try {
      this.studentDao.save(student);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    System.out.println(">>> Student end =" + student);
    return student;
  }
}