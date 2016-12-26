package com.katruk.service;

import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;

import java.util.Collection;

public interface SubjectService {

  Collection<Subject> getSubjects();

  Collection<Subject> getSubjectByTeacher(Teacher teacher);

  Collection<Subject> getSubjectsByStudent(Student student);

}
