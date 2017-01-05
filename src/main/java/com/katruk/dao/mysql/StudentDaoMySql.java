package com.katruk.dao.mysql;

import com.katruk.dao.StudentDao;
import com.katruk.entity.Student;
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

public final class StudentDaoMySql implements StudentDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public StudentDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(StudentDaoMySql.class);
  }

  @Override
  public Collection<Student> getAllStudent() throws DaoException {
    final Collection<Student> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_STUDENT))) {
        result = getStudentByStatement(statement);
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
  public Optional<Student> getStudentById(final Long studentId) throws DaoException {
    final Optional<Student> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_STUDENT_BY_ID))) {
        statement.setLong(1, studentId);
        result = getStudentByStatement(statement).stream().findFirst();
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
  public Student save(final Student student) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.REPLACE_STUDENT))) {
        statement.setLong(1, student.getId());
        statement.setString(2, student.getForm().name());
        statement.setString(3, student.getContract().name());
        statement.executeUpdate();
        connection.commit();
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
    return student;
  }

  private Collection<Student> getStudentByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Student> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Student student = new Student();
        User user = new User();
        user.setId(resultSet.getLong("user_person_id"));
        student.setUser(user);
        student.setContract(Student.Contract.valueOf(resultSet.getString("contract")));
        student.setForm(Student.Form.valueOf(resultSet.getString("form")));
        result.add(student);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}