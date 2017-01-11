package com.katruk.service;

import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface EvaluationService {

  Collection<Evaluation> getEvaluationBySubjects(final Long subjectId) throws ServiceException;

  Collection<Evaluation> getEvaluationByStudent(final Long studentId) throws ServiceException;

  Evaluation save(final Evaluation evaluation) throws ServiceException;

}
