package com.katruk.dao.mysql;

import com.katruk.dao.EvaluationDao;
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

public final class EvaluationDaoMySql implements EvaluationDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public EvaluationDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(EvaluationDaoMySql.class);
  }

  @Override
  public Optional<Evaluation> getEvaluationById(final Long subjectId, final Long studentId)
      throws DaoException {
    final Optional<Evaluation> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_ID))) {
        statement.setLong(1, subjectId);
        statement.setLong(1, studentId);
        result = getEvaluationByStatement(statement).stream().findFirst();
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }

  @Override
  public Collection<Evaluation> getEvaluationByStudent(final Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_STUDENT))) {
        statement.setLong(1, studentId);
        return getEvaluationByStatement(statement);
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public Collection<Evaluation> getEvaluationBySubject(final Long subjectId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_EVALUATION_BY_SUBJECT))) {
        statement.setLong(1, subjectId);
        return getEvaluationByStatement(statement);
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public Evaluation save(final Evaluation evaluation) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.CREATE_EVALUATION),
                            Statement.RETURN_GENERATED_KEYS)) {
        statement.setLong(1, evaluation.getSubject().getId());
        statement.setLong(2, evaluation.getStudent().getId());
        statement.setString(3, evaluation.getStatus().name());
        statement.setString(4, evaluation.getRating().name());
        statement.setString(5, evaluation.getFeedback());
        statement.execute();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            evaluation.setId(generatedKeys.getLong(1));
          } else {
            throw new SQLException("Creating user failed, no ID obtained.");
          }
        }
        connection.commit();
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return evaluation;
  }

  private Collection<Evaluation> getEvaluationByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Evaluation> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Evaluation evaluation = new Evaluation();
        Subject subject = new Subject();
        subject.setId(resultSet.getLong("subject_id"));
        Student student = new Student();
        student.setId(resultSet.getLong("student_user_person_id"));
        evaluation.setId(resultSet.getLong("id"));
        evaluation.setSubject(subject);
        evaluation.setStudent(student);
        evaluation.setStatus(Evaluation.Status.valueOf(resultSet.getString("status")));
        evaluation.setRating(Evaluation.Rating.valueOf(resultSet.getString("rating")));
        evaluation.setFeedback(resultSet.getString("feedback"));
        result.add(evaluation);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}