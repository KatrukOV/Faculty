package com.katruk.dao;

import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;

public interface TeacherDao {

  Teacher getTeacherById(Long teacherId) throws DaoException;

}
