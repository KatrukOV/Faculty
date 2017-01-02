package com.katruk.service;

import com.katruk.entity.Teacher;
import com.katruk.exception.ServiceException;

public interface TeacherService {

  Teacher getTeacherById(final Long teacherId) throws ServiceException;

}
