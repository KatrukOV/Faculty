package com.katruk.dao.mysql;

import com.katruk.dao.SubjectDao;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
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

public final class SubjectDaoMySql implements SubjectDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public SubjectDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(SubjectDaoMySql.class);
  }

  @Override
  public Optional<Subject> getSubjectById(final Long subjectId) throws DaoException {
    final Optional<Subject> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_SUBJECT_BY_ID))) {
        statement.setLong(1, subjectId);
        result = getSubjectByStatement(statement).stream().findFirst();
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
  public Collection<Subject> getSubjectByTeacher(final Teacher teacher) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_SUBJECT_BY_TEACHER))) {
        statement.setLong(1, teacher.getId());
        return getSubjectByStatement(statement);
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
  public Subject save(final Subject subject) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.CREATE_SUBJECT),
                            Statement.RETURN_GENERATED_KEYS)) {
        statement.setLong(1, subject.getTeacher().getId());
        statement.setString(2, subject.getTitle());
        statement.execute();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            subject.setId(generatedKeys.getLong(1));
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
    return subject;
  }

  private Collection<Subject> getSubjectByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Subject> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getLong("teacher_user_person_id"));
        subject.setTeacher(teacher);
        subject.setTitle(resultSet.getString("title"));
        subject.setId(resultSet.getLong("id"));
        result.add(subject);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}