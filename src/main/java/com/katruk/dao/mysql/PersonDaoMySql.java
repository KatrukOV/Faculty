package com.katruk.dao.mysql;

import static java.util.Objects.isNull;

import com.katruk.dao.PersonDao;
import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
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

public final class PersonDaoMySql implements PersonDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public PersonDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(PersonDaoMySql.class);
  }

  @Override
  public Optional<Person> getPersonById(final Long personId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_PERSON_BY_ID))) {
      statement.setLong(1, personId);
      return getPersonByStatement(statement).stream().findFirst();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get person by id: %d.", personId), e);
      throw new DaoException(String.format("Cannot get person by id: %d.", personId), e);
    }
  }

  @Override
  public Collection<Person> getAllPerson() throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_PERSON))) {
      return getPersonByStatement(statement);
    } catch (SQLException e) {
      logger.error("Cannot get all persons.", e);
      throw new DaoException("Cannot get all persons.", e);
    }
  }

  @Override
  public Person save(final Person person) throws DaoException {
    Person result;
    if (isNull(person.id())) {
      result = insert(person);
    } else {
      result = update(person);
    }
    return result;
  }

  private Person insert(Person person) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      person = insertAndCommitOrRollback(person, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot insert person.", e);
      throw new DaoException("Cannot insert person.", e);
    }
    return person;
  }

  private Person insertAndCommitOrRollback(Person person, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.CREATE_PERSON),
                          Statement.RETURN_GENERATED_KEYS)) {
      fillInsertPersonStatement(person, statement);
      new CheckExecuteUpdate(statement, "Creating person failed, no rows affected.").check();
      connection.commit();
      return getAndSetId(person, statement);
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillInsertPersonStatement(Person person, PreparedStatement statement)
      throws SQLException {
    statement.setString(1, person.lastName());
    statement.setString(2, person.name());
    statement.setString(3, person.patronymic());
  }

  private Person getAndSetId(Person person, PreparedStatement statement) throws SQLException {
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      return person.addId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating person failed, no ID obtained.");
    }
  }

  private Person update(Person person) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      updateAndCommitOrRollback(person, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot update person.", e);
      throw new DaoException("Cannot update person.", e);
    }
    return person;
  }

  private void updateAndCommitOrRollback(Person person, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.UPDATE_PERSON))) {
      fillUpdatePersonStatement(person, statement);
      new CheckExecuteUpdate(statement, "Updating person failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillUpdatePersonStatement(Person person, PreparedStatement statement)
      throws SQLException {
    statement.setString(1, person.lastName());
    statement.setString(2, person.name());
    statement.setString(3, person.patronymic());
    statement.setLong(4, person.id());
  }

  private Collection<Person> getPersonByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Person> persons = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Person person = getPerson(resultSet);
        persons.add(person);
      }
    } catch (SQLException e) {
      logger.error("Cannot get person by statement.", e);
      throw new DaoException("Cannot get person by statement.", e);
    }
    return persons;
  }

  private Person getPerson(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getLong(ID);
    String lastName = resultSet.getString(LAST_NAME);
    String name = resultSet.getString(NAME);
    String patronymic = resultSet.getString(PATRONYMIC);
    return new Person(id, lastName, name, patronymic);
  }
}