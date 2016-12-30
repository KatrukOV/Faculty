package com.katruk.service.impl;

import com.katruk.dao.StudentDao;
import com.katruk.dao.TeacherDao;
import com.katruk.dao.mysql.StudentDaoMySql;
import com.katruk.dao.mysql.TeacherDaoMySql;
import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;
import com.katruk.service.TeacherService;

import org.apache.log4j.Logger;

public class TeacherServiceImpl implements TeacherService {

  private final TeacherDao teacherDao;
  private final Logger logger;

  public TeacherServiceImpl() {
    this.teacherDao = new TeacherDaoMySql();
    this.logger = Logger.getLogger(TeacherServiceImpl.class);
  }

  @Override
  public Teacher getTeacherById(Long teacherId) throws ServiceException {
    Teacher teacher;
    try {
      teacher = this.teacherDao.getTeacherById(teacherId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return teacher;
  }
}
