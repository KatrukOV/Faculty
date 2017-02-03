package com.katruk.dao.mysql;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.dao.EvaluationDao;
import com.katruk.dao.mysql.duplCode.CheckExecuteUpdate;
import com.katruk.dao.mysql.duplCode.GetStudent;
import com.katruk.dao.mysql.duplCode.GetSubject;
import com.katruk.entity.Evaluation;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public final class EvaluationDaoMySql implements EvaluationDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public EvaluationDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(EvaluationDaoMySql.class);
  }

  private Evaluation getEvaluationBySubjectIdAndStudentId(
      final Long subjectId, final Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_SUBJECT_AND_STUDENT))) {
      statement.setLong(1, subjectId);
      statement.setLong(2, studentId);
      return getEvaluationByStatement(statement).iterator().next();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get evaluation by subject Id: %d and student Id: %d.",
                                 subjectId, studentId), e);
      throw new DaoException(String.format(
          "Cannot get evaluation by subject Id: %d and student Id: %d.", subjectId, studentId), e);
    }
  }

  @Override
  public Evaluation getEvaluationById(final Long evaluationId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_ID))) {
      statement.setLong(1, evaluationId);
      return getEvaluationByStatement(statement).iterator().next();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get evaluation by id: %d.", evaluationId), e);
      throw new DaoException(String.format("Cannot get evaluation by id: %d.", evaluationId), e);
    }
  }

  @Override
  public Collection<Evaluation> getEvaluationByStudent(final Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_STUDENT))) {
      statement.setLong(1, studentId);
      return getEvaluationByStatement(statement);
    } catch (SQLException e) {
      logger.error(String.format("Cannot get evaluations by student with id: %d.", studentId), e);
      throw new DaoException(
          String.format("Cannot get evaluations by student with id: %d.", studentId), e);
    }
  }

  @Override
  public Collection<Evaluation> getEvaluationBySubject(final Long subjectId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_SUBJECT))) {
      statement.setLong(1, subjectId);
      return getEvaluationByStatement(statement);
    } catch (SQLException e) {
      logger.error(String.format("Cannot get evaluations by subject with id: %d.", subjectId), e);
      throw new DaoException(
          String.format("Cannot get evaluations by subject with id: %d.", subjectId), e);
    }
  }

  @Override
  public Evaluation save(final Evaluation evaluation) throws DaoException {
    // TODO: 17.01.2017  bed logic
    Evaluation result;
    try {
      result = getEvaluationBySubjectIdAndStudentId(evaluation.getSubject().getId(),
                                                    evaluation.getStudent().getId());
    } catch (DaoException e) {
      result = null;
    }
    if (nonNull(result)) {
      evaluation.setId(result.getId());
    }
    if (isNull(evaluation.getId())) {
      result = insert(evaluation);
    } else {
      result = update(evaluation);
    }
    return result;
  }

  private Evaluation insert(Evaluation evaluation) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      insertAndCommitOrRollback(evaluation, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot insert evaluation.", e);
      throw new DaoException("Cannot insert evaluation.", e);
    }
    return evaluation;
  }

  private void insertAndCommitOrRollback(Evaluation evaluation, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection.prepareStatement(
        Sql.getInstance().get(Sql.CREATE_EVALUATION), Statement.RETURN_GENERATED_KEYS)) {
      fillInsertEvaluationStatement(evaluation, statement);
      new CheckExecuteUpdate(statement, "Creating evaluation failed, no rows affected.").check();
      connection.commit();
      getAndSetId(evaluation, statement);
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillInsertEvaluationStatement(Evaluation evaluation, PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, evaluation.getSubject().getId());
    statement.setLong(2, evaluation.getStudent().getId());
    statement.setString(3, evaluation.getStatus().name());
    statement.setString(4, evaluation.getRating() != null ? evaluation.getRating().name() : null);
    statement.setString(5, evaluation.getFeedback());
  }

  private void getAndSetId(Evaluation evaluation, PreparedStatement statement) throws SQLException {
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      evaluation.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating evaluation failed, no ID obtained.");
    }
  }

  private Evaluation update(Evaluation evaluation) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      updateAndCommitOrRollback(evaluation, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot update evaluation.", e);
      throw new DaoException("Cannot update evaluation.", e);
    }
    return evaluation;
  }

  private void updateAndCommitOrRollback(Evaluation evaluation, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.UPDATE_EVALUATION))) {
      fillUpdateEvaluationStatement(evaluation, statement);
      new CheckExecuteUpdate(statement, "Updating evaluation failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillUpdateEvaluationStatement(Evaluation evaluation, PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, evaluation.getSubject().getId());
    statement.setLong(2, evaluation.getStudent().getId());
    statement.setString(3, evaluation.getStatus().name());
    statement.setString(4, evaluation.getRating() != null ? evaluation.getRating().name() : null);
    statement.setString(5, evaluation.getFeedback());
    statement.setLong(6, evaluation.getId());
  }

  private Collection<Evaluation> getEvaluationByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Evaluation> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Evaluation evaluation = getEvaluation(resultSet);
        result.add(evaluation);
      }
    } catch (SQLException e) {
      logger.error("Cannot get evaluation by statement.", e);
      throw new DaoException("Cannot get evaluation by statement.", e);
    }
    return result;
  }

  private Evaluation getEvaluation(ResultSet resultSet) throws SQLException {
    Evaluation evaluation = new Evaluation();
    Subject subject = new GetSubject(resultSet).get();
/*
    Long subjectId = resultSet.getLong(SUBJECT_ID);
    Subject subject = new BaseSubject(subjectId);
 */
    Student student = new GetStudent(resultSet).get();
    evaluation.setId(resultSet.getLong(ID));
    evaluation.setSubject(subject);
    evaluation.setStudent(student);
    evaluation.setStatus(Evaluation.Status.valueOf(resultSet.getString(STATUS)));
    if (nonNull(resultSet.getString(RATING))) {
      evaluation.setRating(Evaluation.Rating.valueOf(resultSet.getString(RATING)));
    }
    evaluation.setFeedback(resultSet.getString(FEEDBACK));
    return evaluation;
  }
}