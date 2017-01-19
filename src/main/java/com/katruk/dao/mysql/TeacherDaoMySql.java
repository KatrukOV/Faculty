package com.katruk.dao.mysql;

import static java.util.Objects.nonNull;

import com.katruk.dao.TeacherDao;
import com.katruk.dao.mysql.ch.CheckExecuteUpdate;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class TeacherDaoMySql implements TeacherDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public TeacherDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(TeacherDaoMySql.class);
  }

  @Override
  public Optional<Teacher> getTeacherById(final Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_TEACHER_BY_ID))) {
      statement.setLong(1, teacherId);
      return getTeacherByStatement(statement).stream().findFirst();
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public Teacher save(final Teacher teacher) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      saveAndCommitOrRollback(teacher, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return teacher;
  }

  private void saveAndCommitOrRollback(Teacher teacher, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.REPLACE_TEACHER))) {
      statement.setLong(1, teacher.getUser().getId());
      statement.setString(2, teacher.getPosition() != null ? teacher.getPosition().name() : null);
      new CheckExecuteUpdate(statement, "Replace teacher failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public void delete(Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      deleteAndCommitOrRollback(teacherId, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  private void deleteAndCommitOrRollback(Long teacherId, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.DELETE_TEACHER))) {
      statement.setLong(1, teacherId);
      new CheckExecuteUpdate(statement, "Delete teacher failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public Collection<Teacher> getAllTeacher() throws DaoException {
    final Collection<Teacher> result;
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_TEACHER))) {
      result = getTeacherByStatement(statement);
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }

  private Collection<Teacher> getTeacherByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Teacher> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Teacher teacher = getTeacher(resultSet);
        result.add(teacher);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }

  private Teacher getTeacher(ResultSet resultSet) throws SQLException {
    Teacher teacher = new Teacher();
    User user = new User();
    user.setId(resultSet.getLong(USER_ID));
    teacher.setId(user.getId());
    teacher.setUser(user);
    String position = resultSet.getString(POSITION);
    if (nonNull(position)) {
      teacher.setPosition(Teacher.Position.valueOf(position));
    }
    return teacher;
  }
}