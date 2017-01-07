package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface StudentDao {

  Collection<Student> getAllStudent() throws DaoException;

  Optional<Student> getStudentById(final Long studentId) throws DaoException;

  Student save(final Student student) throws DaoException;

  void delete(final Long studentId) throws DaoException;
}
