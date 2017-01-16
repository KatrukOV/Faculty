package com.katruk.dao.mysql;

import com.katruk.dao.PeriodDao;
import com.katruk.entity.Period;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class PeriodDaoMySql implements PeriodDao {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public PeriodDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(PeriodDaoMySql.class);
  }

  @Override
  public Optional<Period> getPeriodById(Long periodId) throws DaoException {
    final Optional<Period> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_PERIOD_BY_ID))) {
        statement.setLong(1, periodId);
        result = getPeriodByStatement(statement).stream().findFirst();
      } catch (SQLException e) {
        logger.error("Cannot prepare statement", e);
        throw new DaoException("Cannot prepare statement", e);
      }
    } catch (SQLException e) {
      logger.error("Cannot get period by ID", e);
      throw new DaoException("Cannot get period by ID", e);
    }
    return result;
  }

  @Override
  public Optional<Period> getLastPeriod() throws DaoException {
    final Optional<Period> result;
    try (Connection connection = this.connectionPool.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.GET_LAST_PERIOD))) {
        result = getPeriodByStatement(statement).stream().findFirst();
      } catch (SQLException e) {
        logger.error("Cannot prepare statement", e);
        throw new DaoException("Cannot prepare statement", e);
      }
    } catch (SQLException e) {
      logger.error("Cannot get last period", e);
      throw new DaoException("Cannot get last period", e);
    }
    return result;
  }

  @Override
  public Period save(Period period) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection
          .prepareStatement(Sql.getInstance().get(Sql.INSERT_PERIOD),
                            Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, period.getStatus().name());
        int affectedRows = statement.executeUpdate();
        connection.commit();
        if (affectedRows == 0) {
          throw new SQLException("Creating period failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            period.setId(generatedKeys.getLong(1));
          } else {
            throw new SQLException("Creating period failed, no ID obtained.");
          }
        }
      } catch (SQLException e) {
        connection.rollback();
        logger.error("Cannot prepare statement", e);
        throw new DaoException("Cannot prepare statement", e);
      }
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot save period", e);
      throw new DaoException("Cannot save period", e);
    }

    return period;
  }

  private Collection<Period> getPeriodByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Period> periods = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Period period = new Period();
        period.setId(resultSet.getLong("id"));
        period.setStatus(Period.Status.valueOf(resultSet.getString("status")));
        Date date = Date.valueOf(resultSet.getString("date"));
        System.out.println("date="+date);
        period.setDate(date);
        periods.add(period);
      }
    } catch (SQLException e) {
      logger.error("Unable to get period by statement", e);
      throw new DaoException("Unable to get period by statement", e);
    }
    return periods;
  }
}