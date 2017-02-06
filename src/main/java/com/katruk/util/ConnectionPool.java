package com.katruk.util;

import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionPool {

  //max amount of thread that are allowed to idle at the same time
  private final static int MAX_AMOUNT_OF_THREAD = 10;
  //max wait amount until request timeout log
  private final static int MAX_WAIT_AMOUNT = 100;
  //max amount of active threads in the INSTANCE
  private final static int MAX_AMOUNT_OF_ACTIVE_THREADS = 10;
  private final static String ERROR_GET_CONNECTION = "Can't get connection";
  private final static ConnectionPool INSTANCE = new ConnectionPool();
  private final DataSource dataSource;
  private final Logger logger;

  private ConnectionPool() {
    DataBaseConfig dbConfig = DataBaseConfig.getInstance();
    PoolProperties poolProperties = new PoolProperties();
    poolProperties.setDriverClassName(dbConfig.getValue(DataBaseConfig.DRIVER));
    poolProperties.setUrl(dbConfig.getValue(DataBaseConfig.URL));
    poolProperties.setUsername(dbConfig.getValue(DataBaseConfig.USERNAME));
    poolProperties.setPassword(dbConfig.getValue(DataBaseConfig.PASSWORD));
    poolProperties.setMaxIdle(MAX_AMOUNT_OF_THREAD);
    poolProperties.setMaxWait(MAX_WAIT_AMOUNT);
    poolProperties.setMaxActive(MAX_AMOUNT_OF_ACTIVE_THREADS);
    this.dataSource = new DataSource();
    this.dataSource.setPoolProperties(poolProperties);
    this.logger = Logger.getLogger(ConnectionPool.class);
  }

  public static ConnectionPool getInstance() {
    return INSTANCE;
  }

  public Connection getConnection() throws DaoException {
    try {
      logger.info("Get connection");
      return dataSource.getConnection();
    } catch (SQLException e) {
      logger.error(ERROR_GET_CONNECTION, e);
      throw new DaoException(ERROR_GET_CONNECTION, e);
    }
  }
}