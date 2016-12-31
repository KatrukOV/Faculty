package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import java.util.Optional;

public interface StudentDao {

  Optional<Student> getStudentById(Long studentId) throws DaoException;

  Student save(Student student) throws DaoException;
}
