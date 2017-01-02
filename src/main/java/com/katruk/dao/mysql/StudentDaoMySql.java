package com.katruk.dao.mysql;

import com.katruk.dao.StudentDao;
import com.katruk.entity.Student;
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

public final class StudentDaoMySql implements StudentDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final static String GET_STUDENT_BY_ID =
      "SELECT s.user_person_id, s.form, s.contract "
      + "FROM student AS s "
      + "WHERE s.user_person_id = ? "
      + "ORDER BY s.user_person_id DESC "
      + "LIMIT 1;";

  private final static String CREATE_STUDENT =
      "INSERT INTO student (user_person_id, form, contract) VALUES (?, ?, ?);";

  public StudentDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(StudentDaoMySql.class);
  }

  @Override
  public Optional<Student> getStudentById(final Long studentId) throws DaoException {
    final Optional<Student> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_STUDENT_BY_ID)) {
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
      try (PreparedStatement statement = connection
          .prepareStatement(CREATE_STUDENT)) {
        statement.setLong(1, student.getId());
        statement.setString(2, student.getForm().name());
        statement.setString(3, student.getContract().name());
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