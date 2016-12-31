package com.katruk.dao;

import com.katruk.entity.Evaluation;
import com.katruk.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

public interface EvaluationDao {

  Optional<Evaluation> getEvaluationById(Long subjectId, Long studentId) throws DaoException;

  Collection<Evaluation> getEvaluationByStudent(Long studentId) throws DaoException;

  Collection<Evaluation> getEvaluationBySubject(Long subjectId) throws DaoException;

  Evaluation save(Evaluation evaluation) throws DaoException;
}
