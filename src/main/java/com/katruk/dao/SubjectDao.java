package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface SubjectDao {

  Collection<Subject> getAllSubject() throws DaoException;

  Optional<Subject> getSubjectById(final Long subjectId) throws DaoException;

  Collection<Subject> getSubjectByTeacher(final Long teacherId) throws DaoException;

  Collection<Subject> getSubjectsByStudent(final Long studentId) throws DaoException;

  Subject save(final Subject subject) throws DaoException;
}
