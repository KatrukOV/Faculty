package com.katruk.dao;

import com.katruk.entity.Evaluation;
import com.katruk.exception.DaoException;

import java.util.Collection;

public interface EvaluationDao {

  Evaluation getEvaluationById(final Long evaluationId) throws DaoException;

  Collection<Evaluation> getEvaluationByStudent(final Long studentId) throws DaoException;

  Collection<Evaluation> getEvaluationBySubject(final Long subjectId) throws DaoException;

  Evaluation save(final Evaluation evaluation) throws DaoException;
}
