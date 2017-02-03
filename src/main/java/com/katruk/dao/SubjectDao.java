package com.katruk.dao;

import com.katruk.entity.Subject;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface SubjectDao {

  Collection<Subject> getAllSubject() throws DaoException;

  Collection<Subject> getSubjectByTeacher(final Long teacherId) throws DaoException;

  Subject getSubjectById(final Long subjectId) throws DaoException;

  Subject save(final Subject subject) throws DaoException;

  void delete(Long subjectId) throws DaoException;
}
