package com.katruk.service;

import com.katruk.entity.Student;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface StudentService {

  Collection<Student> getAll() throws ServiceException;

  Student getStudentById(final Long studentId) throws ServiceException;

  Student save(final Student student) throws ServiceException;
}
