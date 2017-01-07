package com.katruk.dao.mysql;

import static java.util.Objects.nonNull;

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
//        connection.rollback();
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
        statement.setLong(1, student.getUser().getId());
        // TODO: 06.01.17 do simple
        statement.setString(2, student.getForm() != null ? student.getForm().name() : null);
        statement.setString(3, student.getContract() != null ? student.getContract().name() : null);
//        statement.setString(4, student.getForm() != null ? student.getForm().name() : null);
//        statement.setString(5, student.getContract() != null ? student.getContract().name() : null);
        System.out.println(">>> SQL= " + statement);
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

  @Override
  public void delete(Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.DELETE_STUDENT))) {
        statement.setLong(1, studentId);
        int affectedRows = statement.executeUpdate();
        connection.commit();
        if (affectedRows == 0) {
          throw new SQLException("Delete student failed, no rows affected.");
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

  private Collection<Student> getStudentByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Student> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Student student = new Student();
        User user = new User();
        user.setId(resultSet.getLong("user_person_id"));
        student.setUser(user);
        student.setId(user.getId());
        String c = resultSet.getString("contract");
        System.out.println(">>> contract="+c);
        if(nonNull(c)){
          student.setContract(Student.Contract.valueOf(resultSet.getString("contract")));
        }
        String f = resultSet.getString("form");
        System.out.println(">>> form="+f);
        if(nonNull(f)){
          student.setForm(Student.Form.valueOf(f));
        }
//        student.setForm(Student.Form.valueOf(resultSet.getString("form")));
        result.add(student);
        System.out.println(">>> stud="+student);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}