package com.katruk.dao.mysql;

import com.katruk.dao.StudentDao;
import com.katruk.dao.TeacherDao;
import com.katruk.entity.Person;
import com.katruk.entity.Student;
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
import java.util.NoSuchElementException;
import java.util.Optional;

public class TeacherDaoMySql implements TeacherDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final String GET_TEACHER_BY_ID =
      "SELECT t.id, t.user_person_id, t.position "
      + "FROM teacher AS t "
      + "WHERE t.user_person_id = ? "
      + "ORDER BY t.id DESC "
      + "LIMIT 1;";

  private final String CREATE_TEACHER =
      "INSERT INTO teacher (user_person_id, position) VALUES (?, ?);";

  public TeacherDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(TeacherDaoMySql.class);
  }

  @Override
  public Optional<Teacher> getTeacherById(Long teacherId) throws DaoException {
    Optional<Teacher> result;
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
  public Teacher save(Teacher teacher) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(CREATE_TEACHER, Statement.RETURN_GENERATED_KEYS)) {
        statement.setLong(1, teacher.getId());
        statement.setString(2, teacher.getPosition().name());
        statement.execute();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            teacher.setId(generatedKeys.getLong(1));
          } else {
            throw new SQLException("Creating user failed, no ID obtained.");
          }
        }
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

  private Collection<Teacher> getTeacherByStatement(PreparedStatement statement)
      throws DaoException {
    Collection<Teacher> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Teacher teacher = new Teacher();
        User user = new User();
        user.setId(resultSet.getLong("user_person_id"));
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
