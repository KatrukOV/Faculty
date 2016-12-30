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
import java.util.NoSuchElementException;

public class TeacherDaoMySql implements TeacherDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final String GET_TEACHER_BY_ID =
      "SELECT p.last_name, p.name, p.patronymic, u.username, u.password, u.role, t.position "
      + "FROM user AS u "
      + "INNER JOIN person AS p "
      + "ON u.person_id = p.id "
      + "INNER JOIN teacher AS t "
      + "ON t.user_person_id = p.id "
      + "WHERE t.user_person_id = ?;";

  public TeacherDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(TeacherDaoMySql.class);
  }

  @Override
  public Teacher getTeacherById(Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_TEACHER_BY_ID)) {

        statement.setLong(1, teacherId);

        return getTeacherByStatement(statement);
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

  private Teacher getTeacherByStatement(PreparedStatement statement) throws DaoException {
    try (ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        Teacher teacher = new Teacher();
        User user = new User();
        Person person = new Person();
        person.setLastName(resultSet.getString("last_name"));
        person.setName(resultSet.getString("name"));
        person.setPatronymic(resultSet.getString("patronymic"));
        user.setPerson(person);
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        teacher.setUser(user);
        teacher.setPosition(Teacher.Position.valueOf(resultSet.getString("position")));
        return teacher;
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    throw new DaoException("Teacher not exist", new NoSuchElementException());
  }
}
