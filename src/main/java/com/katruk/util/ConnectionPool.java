package com.katruk.util;

import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

  //max amount of thread that are allowed to idle at the same time
  private final int MAX_AMOUNT_OF_THREAD = 10;
  //max wait amount until request timeout exception
  private final int MAX_WAIT_AMOUNT = 100;
  //max amount of active threads in the POOL
  private final int MAX_AMOUNT_OF_ACTIVE_THREADS = 10;

  private final String ERROR_GET_CONNECTION = "Can't get connection";

  private final static ConnectionPool POOL = new ConnectionPool();

  private final DataSource dataSource;

//  private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

  private ConnectionPool() {
    Config config = Config.getInstance();
    PoolProperties poolProperties = new PoolProperties();
    poolProperties.setUrl(config.getValue(Config.URL));
    poolProperties.setDriverClassName(config.getValue(Config.DRIVER));
    poolProperties.setMaxIdle(MAX_AMOUNT_OF_THREAD);
    poolProperties.setMaxWait(MAX_WAIT_AMOUNT);
    poolProperties.setMaxActive(MAX_AMOUNT_OF_ACTIVE_THREADS);
    this.dataSource = new DataSource();
    this.dataSource.setPoolProperties(poolProperties);
  }

  public static ConnectionPool getInstance() {
    return POOL;
  }

  public Connection getConnection() throws DaoException {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
//      LOGGER.error(ERROR_GET_CONNECTION, e);
      throw new DaoException(ERROR_GET_CONNECTION, e);
    }
  }
}