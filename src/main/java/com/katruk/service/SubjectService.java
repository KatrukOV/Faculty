package com.katruk.service;

import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface SubjectService {

  Collection<Subject> getAll() throws ServiceException;

  Subject getSubjectById(final Long subjectId) throws ServiceException;

  Collection<Subject> getSubjectByTeacher(final Teacher teacher) throws ServiceException;

  Subject save(final Subject subject) throws ServiceException;

  void remove(final Long subjectId) throws ServiceException;
}
