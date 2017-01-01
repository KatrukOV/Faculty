package com.katruk.service;

import com.katruk.entity.Student;
import com.katruk.entity.User;
import com.katruk.entity.dto.UserDto;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;

public interface StudentService {

  Student getStudentById(final Long studentId) throws ServiceException;

}
