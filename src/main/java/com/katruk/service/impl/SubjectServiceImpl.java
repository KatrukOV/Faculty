package com.katruk.service.impl;

import com.katruk.dao.SubjectDao;
import com.katruk.dao.mysql.SubjectDaoMySql;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    Collection<Subject> subjects;
    try {
      subjects = this.subjectDao.getAllSubject();
    } catch (DaoException e) {
      logger.error("Cannot get all subjects.", e);
      throw new ServiceException("Cannot get all subjects.", e);
    }
    Collection<Teacher> teachers = this.teacherService.getAll();
    for (Subject subject : subjects) {
      teachers.stream()
          .filter(teacher -> Objects.equals(subject.getTeacher().getId(), teacher.getId()))
          .forEach(subject::setTeacher);
    }
    return subjects;
  }

  @Override
  public Subject getSubjectById(final Long subjectId) throws ServiceException {
    final Subject subject;
    try {
      subject = this.subjectDao.getSubjectById(subjectId)
          .orElseThrow(() -> new DaoException("Subject not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error(String.format("Cannot get subject by id: %d.", subjectId), e);
      throw new ServiceException(String.format("Cannot get subject by id: %d.", subjectId), e);
    }
    final Teacher teacher = this.teacherService.getTeacherById(subject.getTeacher().getId());
    subject.setTeacher(teacher);
    return subject;
  }

  @Override
  public Collection<Subject> getSubjectByTeacher(final Teacher teacher) throws ServiceException {
    Collection<Subject> subjects;
    try {
      subjects = this.subjectDao.getSubjectByTeacher(teacher.getId());
    } catch (DaoException e) {
      logger.error(
          String.format("Cannot get subjects by teacher with id: %d.", teacher.getId()), e);
      throw new ServiceException(
          String.format("Cannot get subjects by teacher with id: %d.", teacher.getId()), e);
    }
    Collection<Teacher> teachers = this.teacherService.getAll();
    for (Subject subject : subjects) {
      teachers.stream()
          .filter(teach -> Objects.equals(subject.getTeacher().getId(), teach.getId()))
          .forEach(subject::setTeacher);
    }
    return subjects;
  }

  @Override
  public Subject save(final Subject subject) throws ServiceException {
    final Subject result;
    try {
      result = this.subjectDao.save(subject);
    } catch (DaoException e) {
      logger.error("Cannot save subject.", e);
      throw new ServiceException("Cannot save subject.", e);
    }
    return result;
  }

  @Override
  public void remove(final Long subjectId) throws ServiceException {
    try {
      this.subjectDao.delete(subjectId);
    } catch (DaoException e) {
      logger.error(String.format("Cannot remove subject with id: %d.", subjectId), e);
      throw new ServiceException(String.format("Cannot remove subject with id: %d.", subjectId), e);
    }
  }
}