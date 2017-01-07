package com.katruk.dao;

import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface TeacherDao {

  Optional<Teacher> getTeacherById(final Long teacherId) throws DaoException;

  Collection<Teacher> getAllTeacher() throws DaoException;

  Teacher save(final Teacher teacher) throws DaoException;

  void delete(final Long teacherId) throws DaoException;
}
