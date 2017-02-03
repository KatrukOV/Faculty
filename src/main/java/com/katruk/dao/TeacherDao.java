package com.katruk.dao;

import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;

import java.util.Collection;

public interface TeacherDao {

  Teacher getTeacherById(final Long teacherId) throws DaoException;

  Collection<Teacher> getAllTeacher() throws DaoException;

  Teacher save(final Teacher teacher) throws DaoException;

  void delete(final Long teacherId) throws DaoException;
}
