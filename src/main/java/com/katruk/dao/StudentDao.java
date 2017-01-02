package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.exception.DaoException;

import java.util.Optional;

public interface StudentDao {

  Optional<Student> getStudentById(final Long studentId) throws DaoException;

  Student save(final Student student) throws DaoException;
}
