package com.katruk.service;

import com.katruk.entity.Evaluation;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface EvaluationService {

  Evaluation getEvaluationById(final Long evaluationId) throws ServiceException;

  Collection<Evaluation> getEvaluationBySubjects(final Long subjectId) throws ServiceException;

  Collection<Evaluation> getEvaluationByStudent(final Long studentId) throws ServiceException;

  Evaluation save(final Evaluation evaluation) throws ServiceException;
}
