package com.katruk.service;

import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;

import java.util.Collection;

public interface EvaluationService {

  Collection<Evaluation> getEvaluationByTeacherAndSubjects(Teacher teacher, Subject subject);

  Collection<Evaluation> getEvaluationByStudentAndSubjects(Student student, Subject subject);

}
