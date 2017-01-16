package com.katruk.dao.mysql;

import static java.util.Objects.isNull;

import com.katruk.dao.PersonDao;
import com.katruk.entity.Person;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

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

  public PersonDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(PersonDaoMySql.class);
  }

  @Override
  public Optional<Person> getPersonById(final Long personId) throws DaoException {
    final Optional<Person> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_PERSON_BY_ID))) {
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
  public Collection<Person> getAllPerson() throws DaoException {
    final Collection<Person> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_PERSON))) {
        result = getPersonByStatement(statement);
      } catch (SQLException e) {
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
    Person result;
    System.out.println("?? per=" + person);
    if (isNull(person.getId())) {
      result = insert(person);
    } else {
      result = update(person);
    }
    return result;
  }

  private Person insert(Person person) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.CREATE_PERSON),
                            Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, person.getLastName());
        statement.setString(2, person.getName());
        statement.setString(3, person.getPatronymic());
        int affectedRows = statement.executeUpdate();
        connection.commit();
        if (affectedRows == 0) {
          throw new SQLException("Creating person failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            person.setId(generatedKeys.getLong(1));
          } else {
            throw new SQLException("Creating person failed, no ID obtained.");
          }
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
    return person;
  }

  private Person update(Person person) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.UPDATE_PERSON))) {
        statement.setString(1, person.getLastName());
        statement.setString(2, person.getName());
        statement.setString(3, person.getPatronymic());
        statement.setLong(4, person.getId());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
          throw new SQLException("Updating person failed, no rows affected.");
        }
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
    return person;
  }

  private Collection<Person> getPersonByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Person> persons = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setLastName(resultSet.getString("last_name"));
        person.setName(resultSet.getString("name"));
        person.setPatronymic(resultSet.getString("patronymic"));
        persons.add(person);
      }
    } catch (SQLException e) {
      logger.error("", e);
      throw new DaoException("", e);
    }
    return persons;
  }
}