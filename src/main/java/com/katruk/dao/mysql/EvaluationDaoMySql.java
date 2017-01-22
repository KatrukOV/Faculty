package com.katruk.dao.mysql;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.dao.EvaluationDao;
import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
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
import java.util.Optional;

public final class EvaluationDaoMySql implements EvaluationDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public EvaluationDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(EvaluationDaoMySql.class);
  }

  @Override
  public Optional<Evaluation> getEvaluationBySubjectIdAndStudentId(
      final Long subjectId, final Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_SUBJECT_AND_STUDENT))) {
      statement.setLong(1, subjectId);
      statement.setLong(2, studentId);
      return getEvaluationByStatement(statement).stream().findFirst();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get evaluation by subject Id: %d and student Id: %d.",
                                 subjectId, studentId), e);
      throw new DaoException(String.format(
          "Cannot get evaluation by subject Id: %d and student Id: %d.", subjectId, studentId), e);
    }
  }

  @Override
  public Optional<Evaluation> getEvaluationById(final Long evaluationId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_ID))) {
      statement.setLong(1, evaluationId);
      return getEvaluationByStatement(statement).stream().findFirst();
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
  public Evaluation save(Evaluation evaluation) throws DaoException {
    // TODO: 17.01.2017  bed logic
    Evaluation result;
    try {
      result = getEvaluationBySubjectIdAndStudentId(evaluation.subject().id(),
                                                    evaluation.student().id()).orElse(null);
    } catch (DaoException e) {
      result = null;
    }
    if (nonNull(result)) {
      evaluation = evaluation.addId(result.id());
    }
    if (isNull(evaluation.id())) {
      result = insert(evaluation);
    } else {
      result = update(evaluation);
    }
    return result;
  }

  private Evaluation insert(Evaluation evaluation) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      evaluation = insertAndCommitOrRollback(evaluation, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot insert evaluation.", e);
      throw new DaoException("Cannot insert evaluation.", e);
    }
    return evaluation;
  }

  private Evaluation insertAndCommitOrRollback(final Evaluation evaluation,
                                               final Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection.prepareStatement(
        Sql.getInstance().get(Sql.CREATE_EVALUATION), Statement.RETURN_GENERATED_KEYS)) {
      fillInsertEvaluationStatement(evaluation, statement);
      new CheckExecuteUpdate(statement, "Creating evaluation failed, no rows affected.").check();
      connection.commit();
      return getAndSetId(evaluation, statement);
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillInsertEvaluationStatement(final Evaluation evaluation,
                                             final PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, evaluation.subject().id());
    statement.setLong(2, evaluation.student().id());
    statement.setString(3, evaluation.status().name());
    statement.setString(4, evaluation.rating() != null ? evaluation.rating().name() : null);
    statement.setString(5, evaluation.feedback());
  }

  private Evaluation getAndSetId(final Evaluation evaluation, final PreparedStatement statement)
      throws SQLException {
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      return evaluation.addId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating evaluation failed, no ID obtained.");
    }
  }

  private Evaluation update(Evaluation evaluation) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      evaluation = updateAndCommitOrRollback(evaluation, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot update evaluation.", e);
      throw new DaoException("Cannot update evaluation.", e);
    }
    return evaluation;
  }

  private Evaluation updateAndCommitOrRollback(Evaluation evaluation,
                                               final Connection connection)
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
    return evaluation;
  }

  private void fillUpdateEvaluationStatement(final Evaluation evaluation,
                                             final PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, evaluation.subject().id());
    statement.setLong(2, evaluation.student().id());
    statement.setString(3, evaluation.status().name());
    statement.setString(4, evaluation.rating() != null ? evaluation.rating().name() : null);
    statement.setString(5, evaluation.feedback());
    statement.setLong(6, evaluation.id());
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

  private Evaluation getEvaluation(final ResultSet resultSet) throws SQLException {
    Long subjectId = resultSet.getLong(SUBJECT_ID);
    Subject subject = new Subject(subjectId);
    Long studentId = resultSet.getLong(STUDENT_ID);
    Student student = new Student(studentId);
    Long id = resultSet.getLong(ID);
    Evaluation.Status status = null;
    if (nonNull(resultSet.getString(STATUS))) {
      status = Evaluation.Status.valueOf(resultSet.getString(STATUS));
    }
    Evaluation.Rating rating = null;
    if (nonNull(resultSet.getString(RATING))) {
      rating = Evaluation.Rating.valueOf(resultSet.getString(RATING));
    }
    String feedback = resultSet.getString(FEEDBACK);
    return new Evaluation(id, subject, student, status, rating, feedback);
  }
}