package com.katruk.service;

import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;

import java.util.Collection;

public interface EvaluationService {

  Collection<Evaluation> getEvaluationByTeacherAndSubjects(final Teacher teacher,
                                                           final Subject subject);

  Collection<Evaluation> getEvaluationByStudentAndSubjects(final Student student,
                                                           final Subject subject);

}
