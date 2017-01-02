package com.katruk.service;

import com.katruk.entity.Student;
import com.katruk.exception.ServiceException;

public interface StudentService {

  Student getStudentById(final Long studentId) throws ServiceException;

  Student create(final Student student) throws ServiceException;
}
