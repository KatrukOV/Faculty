package com.katruk.service.impl;

import com.katruk.dao.TeacherDao;
import com.katruk.dao.mysql.TeacherDaoMySql;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;

public final class TeacherServiceImpl implements TeacherService {

  private final TeacherDao teacherDao;
  private final UserService userService;
  private final Logger logger;

  public TeacherServiceImpl() {
    this.teacherDao = new TeacherDaoMySql();
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(TeacherServiceImpl.class);
  }

  @Override
  public Teacher getTeacherById(final Long teacherId) throws ServiceException {
    final Teacher teacher;
    try {
      teacher = this.teacherDao.getTeacherById(teacherId)
          .orElseThrow(() -> new DaoException("Teacher not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final User user = this.userService.getUserById(teacher.getUser().getId());
    teacher.setUser(user);
    return teacher;
  }

  @Override
  public Collection<Teacher> gatAll() throws ServiceException {
    Collection<Teacher> result;
    try {
      result = this.teacherDao.getAllTeacher();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return result;
  }
}
