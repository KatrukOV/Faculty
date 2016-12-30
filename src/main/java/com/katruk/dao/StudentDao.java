package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;

public interface StudentDao {

  Student getStudentById(Long studentId) throws DaoException;

}
