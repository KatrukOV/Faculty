package com.katruk.dao.mysql;

import com.katruk.dao.StudentDao;
import com.katruk.entity.Person;
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
import java.util.Optional;

public class StudentDaoMySql implements StudentDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final String GET_STUDENT_BY_ID =
      "SELECT "
      + "p.id, p.last_name, p.name, p.patronymic, u.username, u.password, u.role, s.contract, s.form "
      + "FROM user AS u "
      + "INNER JOIN person AS p "
      + "ON u.person_id = p.id "
      + "INNER JOIN student AS s "
      + "ON s.user_person_id = p.id "
      + "WHERE s.user_person_id = ? "
      + "ORDER BY p.id DESC "
      + "LIMIT 1;";

  private final String CREATE_STUDENT =
      "INSERT INTO student (user_person_id, form, contract) VALUES (?, ?, ?);";

  public StudentDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(StudentDaoMySql.class);
  }

  @Override
  public Optional<Student> getStudentById(Long studentId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_STUDENT_BY_ID)) {
        statement.setLong(1, studentId);
        return getStudentByStatement(statement);
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
  public Student save(Student student) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
        statement.setLong(1, student.getUser().getPerson().getId());
        statement.setString(2, student.getForm().name());
        statement.setString(3, student.getContract().name());
        statement.execute();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            student.setId(generatedKeys.getLong(1));
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
    return student;
  }

  private Optional<Student> getStudentByStatement(PreparedStatement statement) throws DaoException {
    Optional<Student> result = Optional.empty();
    try (ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        Student student = new Student();
        User user = new User();
        Person person = new Person();
        person.setLastName(resultSet.getString("last_name"));
        person.setName(resultSet.getString("name"));
        person.setPatronymic(resultSet.getString("patronymic"));
        user.setPerson(person);
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        student.setUser(user);
        student.setContract(Student.Contract.valueOf(resultSet.getString("contract")));
        student.setForm(Student.Form.valueOf(resultSet.getString("contract")));
        result = Optional.of(student);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}
