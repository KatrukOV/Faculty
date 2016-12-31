package com.katruk.dao.mysql;

import com.katruk.dao.PersonDao;
import com.katruk.entity.Person;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class PersonDaoMySql implements PersonDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final String CREATE_PERSON =
      "INSERT INTO person (last_name, name, patronymic) VALUES (?, ?, ?);";

  private final String GET_PERSON_BY_ID =
      "SELECT id, last_name, name, patronymic "
      + "FROM person "
      + "WHERE id = ? "
      + "ORDER BY id DESC "
      + "LIMIT 1;";

  public PersonDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(PersonDaoMySql.class);
  }

  @Override
  public Optional<Person> getPersonById(Long personId) throws DaoException {

    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_PERSON_BY_ID)) {
        statement.setLong(1, personId);
        return getPersonByStatement(statement);
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
  public Person save(Person person) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, person.getLastName());
        statement.setString(2, person.getName());
        statement.setString(3, person.getPatronymic());
        statement.execute();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            person.setId(generatedKeys.getLong(1));
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
    return person;
  }

  private Optional<Person> getPersonByStatement(PreparedStatement statement) throws DaoException {
    Optional<Person> result = Optional.empty();
    try (ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        Person person = new Person();
        person.setLastName(resultSet.getString("last_name"));
        person.setName(resultSet.getString("name"));
        person.setPatronymic(resultSet.getString("patronymic"));
        result = Optional.of(person);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}