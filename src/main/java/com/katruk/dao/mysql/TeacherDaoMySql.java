package com.katruk.dao.mysql;

import com.katruk.dao.TeacherDao;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class TeacherDaoMySql implements TeacherDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final static String GET_TEACHER_BY_ID =
      "SELECT t.user_person_id, t.position "
      + "FROM teacher AS t "
      + "WHERE t.user_person_id = ? "
      + "ORDER BY t.id DESC "
      + "LIMIT 1;";

  private final static String CREATE_TEACHER =
      "INSERT INTO teacher (user_person_id, position) VALUES (?, ?);";

  public TeacherDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(TeacherDaoMySql.class);
  }

  @Override
  public Optional<Teacher> getTeacherById(final Long teacherId) throws DaoException {
    final Optional<Teacher> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_TEACHER_BY_ID)) {
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
      try (PreparedStatement statement = connection
          .prepareStatement(CREATE_TEACHER)) {
        statement.setLong(1, teacher.getId());
        statement.setString(2, teacher.getPosition().name());
        statement.execute();
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
    return teacher;
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
        teacher.setPosition(Teacher.Position.valueOf(resultSet.getString("position")));
        result.add(teacher);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}