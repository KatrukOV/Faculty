package com.katruk.dao.mysql;

import static java.util.Objects.nonNull;

import com.katruk.dao.TeacherDao;
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

public final class TeacherDaoMySql implements TeacherDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public TeacherDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(TeacherDaoMySql.class);
  }

  @Override
  public Optional<Teacher> getTeacherById(final Long teacherId) throws DaoException {
    final Optional<Teacher> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_TEACHER_BY_ID))) {
        statement.setLong(1, teacherId);
        result = getTeacherByStatement(statement).stream().findFirst();
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
  public Teacher save(final Teacher teacher) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.REPLACE_TEACHER))) {
        statement.setLong(1, teacher.getUser().getId());
        statement.setString(2, teacher.getPosition() != null ? teacher.getPosition().name() : null);
        int affectedRows = statement.executeUpdate();
        connection.commit();
        if (affectedRows == 0) {
          throw new SQLException("Replace teacher failed, no rows affected.");
        }
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return teacher;
  }

  @Override
  public void delete(Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.DELETE_TEACHER))) {
        statement.setLong(1, teacherId);
        int affectedRows = statement.executeUpdate();
        connection.commit();
        if (affectedRows == 0) {
          throw new SQLException("Delete teacher failed, no rows affected.");
        }
      } catch (SQLException e) {
        connection.rollback();
        logger.error("", e);
        throw new DaoException("", e);
      }
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
  }

  @Override
  public Collection<Teacher> getAllTeacher() throws DaoException {
    final Collection<Teacher> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_TEACHER))) {
        result = getTeacherByStatement(statement);
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

  private Collection<Teacher> getTeacherByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Teacher> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Teacher teacher = new Teacher();
        User user = new User();
        user.setId(resultSet.getLong("user_person_id"));
        teacher.setId(user.getId());
        teacher.setUser(user);
        String position = resultSet.getString("position");
        if (nonNull(position)) {
          teacher.setPosition(Teacher.Position.valueOf(position));
        }
        result.add(teacher);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}