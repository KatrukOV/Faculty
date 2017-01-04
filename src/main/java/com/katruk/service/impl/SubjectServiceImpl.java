package com.katruk.service.impl;

import com.katruk.dao.StudentDao;
import com.katruk.dao.SubjectDao;
import com.katruk.dao.mysql.StudentDaoMySql;
import com.katruk.dao.mysql.SubjectDaoMySql;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;

public final class SubjectServiceImpl implements SubjectService {

  private final SubjectDao subjectDao;
  private final TeacherService teacherService;
  private final Logger logger;

  public SubjectServiceImpl() {
    this.subjectDao = new SubjectDaoMySql();
    this.teacherService = new TeacherServiceImpl();
    this.logger = Logger.getLogger(SubjectServiceImpl.class);
  }

  @Override
  public Collection<Subject> getAll() throws ServiceException {
    Collection<Subject> result;
    result = this.subjectDao.
    return result;
  }

  @Override
  public Subject getSubjectById(Long subjectId) throws ServiceException {
    final Subject subject;
    try {
      subject = this.subjectDao.getSubjectById(subjectId)
          .orElseThrow(() -> new DaoException("Subject not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final Teacher teacher = this.teacherService.getTeacherById(subject.getTeacher().getId());
    subject.setTeacher(teacher);
    return subject;
  }

  @Override
  public Collection<Subject> getSubjectByTeacher(Teacher teacher) throws ServiceException {
    return null;
  }

  @Override
  public Collection<Subject> getSubjectsByStudent(Student student) throws ServiceException {
    return null;
  }

  @Override
  public Subject create(Subject subject) throws ServiceException {
    try {
      this.subjectDao.save(subject);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return subject;
  }
}