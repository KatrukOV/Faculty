package com.katruk.dao.mysql;

import static java.util.Objects.isNull;

import com.katruk.dao.SubjectDao;
import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
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

public final class SubjectDaoMySql implements SubjectDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public SubjectDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(SubjectDaoMySql.class);
  }

  @Override
  public Collection<Subject> getAllSubject() throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_SUBJECT))) {
      return getSubjectByStatement(statement);
    } catch (SQLException e) {
      logger.error("Cannot get all subjects.", e);
      throw new DaoException("Cannot get all subjects.", e);
    }
  }

  @Override
  public Optional<Subject> getSubjectById(final Long subjectId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_SUBJECT_BY_ID))) {
      statement.setLong(1, subjectId);
      return getSubjectByStatement(statement).stream().findFirst();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get subject by id: %d.", subjectId), e);
      throw new DaoException(String.format("Cannot get subject by id: %d.", subjectId), e);
    }
  }

  @Override
  public Collection<Subject> getSubjectByTeacher(final Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_SUBJECT_BY_TEACHER))) {
      statement.setLong(1, teacherId);
      return getSubjectByStatement(statement);
    } catch (SQLException e) {
      logger.error(String.format("Cannot get subjects by teacher with id: %d.", teacherId), e);
      throw new DaoException(String.format
          ("Cannot get subjects by teacher with id: %d.", teacherId), e);
    }
  }

  @Override
  public Collection<Subject> getSubjectsByStudent(final Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_SUBJECT_BY_STUDENT))) {
      statement.setLong(1, studentId);
      return getSubjectByStatement(statement);
    } catch (SQLException e) {
      logger.error(String.format("Cannot get subjects by student with id: %d.", studentId), e);
      throw new DaoException(String.format
          ("Cannot get subjects by student with id: %d.", studentId), e);
    }
  }

  @Override
  public Subject save(final Subject subject) throws DaoException {
    Subject result;
    if (isNull(subject.getId())) {
      result = insert(subject);
    } else {
      result = update(subject);
    }
    return result;
  }

  @Override
  public void delete(Long subjectId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      deleteAndCommitOrRollback(subjectId, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error(String.format("Cannot delete subject with id: %d.", subjectId), e);
      throw new DaoException(String.format("Cannot delete subject with id: %d.", subjectId), e);
    }
  }

  private void deleteAndCommitOrRollback(Long subjectId, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.DELETE_SUBJECT))) {
      statement.setLong(1, subjectId);
      new CheckExecuteUpdate(statement, "Remove teacher failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private Subject insert(Subject subject) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      insertAndCommitOrRollback(subject, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot insert subject.", e);
      throw new DaoException("Cannot insert subject.", e);
    }
    return subject;
  }

  private void insertAndCommitOrRollback(Subject subject, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection.prepareStatement(
        Sql.getInstance().get(Sql.CREATE_SUBJECT), Statement.RETURN_GENERATED_KEYS)) {
      statement.setLong(1, subject.getTeacher().getId());
      statement.setString(2, subject.getTitle());
      new CheckExecuteUpdate(statement, "Creating subject failed, no rows affected.").check();
      connection.commit();
      getAndSetId(subject, statement);
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void getAndSetId(Subject subject, PreparedStatement statement) throws SQLException {
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      subject.setId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating subject failed, no ID obtained.");
    }
  }

  private Subject update(Subject subject) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      updateAndCommitOrRollback(subject, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot update subject.", e);
      throw new DaoException("Cannot update subject.", e);
    }
    return subject;
  }

  private void updateAndCommitOrRollback(Subject subject, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.UPDATE_SUBJECT))) {
      fillUpdateSubjectStatement(subject, statement);
      new CheckExecuteUpdate(statement, "Updating subject failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillUpdateSubjectStatement(Subject subject, PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, subject.getTeacher().getId());
    statement.setString(2, subject.getTitle());
    statement.setLong(3, subject.getId());
  }

  private Collection<Subject> getSubjectByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Subject> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Subject subject = getSubject(resultSet);
        result.add(subject);
      }
    } catch (SQLException e) {
      logger.error("Cannot get subject by statement.", e);
      throw new DaoException("Cannot get subject by statement.", e);
    }
    return result;
  }

  private Subject getSubject(ResultSet resultSet) throws SQLException {
    Subject subject = new Subject(title, teacher);
    Teacher teacher = new Teacher(user, position);
    teacher.setId(resultSet.getLong(TEACHER_ID));
    subject.setTeacher(teacher);
    subject.setTitle(resultSet.getString(TITLE));
    subject.setId(resultSet.getLong(ID));
    return subject;
  }
}