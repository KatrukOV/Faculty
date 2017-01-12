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
import java.util.Objects;

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
    System.out.println("<< stu=" + students.size() + " stu=" + students);
    Collection<User> users = this.userService.getAll();
    System.out.println("<< us=" + users.size() + " us=" + users);
    for (User user : users) {
      students.stream().filter(student -> Objects.equals(user.getId(), student.getUser().getId()))
          .forEach(student -> {
            student.setUser(user);
            System.out.println(">,, st=" + student);
          });
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
    System.out.println(">.. student=" + student);
    return student;
  }

  @Override
  public Student save(final Student student) throws ServiceException {
    try {
      this.studentDao.save(student);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    System.out.println(">>> Student end =" + student);
    return student;
  }

  @Override
  public void remove(final Long studentId) throws ServiceException {
    try {
      this.studentDao.delete(studentId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
  }
}