package com.katruk.dao.mysql;

import static java.util.Objects.nonNull;
import com.katruk.dao.StudentDao;
import com.katruk.dao.UserDao;
import com.katruk.dao.mysql.duplCode.CheckExecuteUpdate;
import com.katruk.dao.mysql.duplCode.GetStudent;
import com.katruk.dao.mysql.duplCode.GetUser;
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
import java.util.NoSuchElementException;

public final class StudentDaoMySql implements StudentDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;
  private final UserDao userDao;

  public StudentDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(StudentDaoMySql.class);
    userDao = new UserDaoMySql();
  }

  @Override
  public Collection<Student> getAllStudent() throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_STUDENT))
    ) {
      return getStudentByStatement(statement);
    } catch (SQLException e) {
      logger.error("Cannot get all students.", e);
      throw new DaoException("Cannot get all students.", e);
    }
  }

  @Override
  public Student getStudentById(final Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_STUDENT_BY_ID))) {
      statement.setLong(1, studentId);
      return getStudentByStatement(statement).stream().iterator().next();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get student by id: %d.", studentId), e);
      throw new DaoException(String.format("Cannot get student by id: %d.", studentId), e);
    }
  }

  @Override
  public Student save(final Student student) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      saveAndCommitOrRollback(student, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot save student.", e);
      throw new DaoException("Cannot save student.", e);
    }
    return student;
  }

  private void saveAndCommitOrRollback(Student student, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.REPLACE_STUDENT))) {
      fillSaveStudentStatement(student, statement);
      new CheckExecuteUpdate(statement, "Replace student failed, no rows affected.").check();
      this.userDao.save(student.getUser());
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillSaveStudentStatement(Student student, PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, student.getUser().getId());
    statement.setString(2, student.getForm() != null ? student.getForm().name() : null);
    statement.setString(3, student.getContract() != null ? student.getContract().name() : null);
  }

  @Override
  public void delete(Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      deleteAndCommitOrRollback(studentId, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error(String.format("Cannot delete student by id: %d.", studentId), e);
      throw new DaoException(String.format("Cannot delete student by id: %d.", studentId), e);
    }
  }

  private void deleteAndCommitOrRollback(Long studentId, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.DELETE_STUDENT))) {
      statement.setLong(1, studentId);
      new CheckExecuteUpdate(statement, "Delete student failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private Collection<Student> getStudentByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Student> students = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Student student = new GetStudent(resultSet).get();
        students.add(student);
      }
    } catch (SQLException e) {
      logger.error("Cannot get student by statement.", e);
      throw new DaoException("Cannot get student by statement.", e);
    }
    if (students.isEmpty()) {
      throw new DaoException("No students by statement.", new NoSuchElementException());
    }
    return students;
  }
}