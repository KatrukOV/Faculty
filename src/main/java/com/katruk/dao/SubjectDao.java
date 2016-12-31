package com.katruk.dao;

import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface SubjectDao {

  Optional<Subject> getSubjectById(Long subjectId) throws DaoException;

  Collection<Subject> getSubjectByTeacher(Teacher teacher) throws DaoException;

  Subject save(Subject subject) throws DaoException;
}
