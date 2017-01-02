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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class PersonDaoMySql implements PersonDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  private final static String CREATE_PERSON =
      "INSERT INTO person (last_name, name, patronymic) VALUES (?, ?, ?);";

  private final static String GET_PERSON_BY_ID =
      "SELECT p.id, p.last_name, p.name, p.patronymic "
      + "FROM person AS p "
      + "WHERE p.id = ? "
      + "ORDER BY p.id DESC "
      + "LIMIT 1;";

  public PersonDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(PersonDaoMySql.class);
  }

  @Override
  public Optional<Person> getPersonById(final Long personId) throws DaoException {
   final Optional<Person> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(GET_PERSON_BY_ID)) {
        statement.setLong(1, personId);
        result = getPersonByStatement(statement).stream().findFirst();
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
  public Person save(final Person person) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, person.getLastName());
        statement.setString(2, person.getName());
        statement.setString(3, person.getPatronymic());
        statement.execute();
        connection.commit();
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

  private Collection<Person> getPersonByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Person> result = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        Person person = new Person();
        person.setLastName(resultSet.getString("last_name"));
        person.setName(resultSet.getString("name"));
        person.setPatronymic(resultSet.getString("patronymic"));
        result.add(person);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return result;
  }
}