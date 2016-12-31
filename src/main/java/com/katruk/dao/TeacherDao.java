package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;

import java.util.Optional;

public interface TeacherDao {

  Optional<Teacher> getTeacherById(Long teacherId) throws DaoException;

  Teacher save(Teacher teacher) throws DaoException;
}
