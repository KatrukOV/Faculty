package com.katruk.service;

import com.katruk.entity.Teacher;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface TeacherService {

  Teacher getTeacherById(final Long teacherId) throws ServiceException;

  Collection<Teacher> gatAll() throws ServiceException;
}
