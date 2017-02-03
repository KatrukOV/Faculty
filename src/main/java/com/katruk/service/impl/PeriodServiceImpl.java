package com.katruk.service.impl;

import com.katruk.dao.PeriodDao;
import com.katruk.dao.mysql.PeriodDaoMySql;
import com.katruk.entity.Period;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.PeriodService;

import org.apache.log4j.Logger;

public final class PeriodServiceImpl implements PeriodService {

  private final Logger logger;
  private final PeriodDao periodDao;

  public PeriodServiceImpl() {
    this.logger = Logger.getLogger(PeriodServiceImpl.class);
    this.periodDao = new PeriodDaoMySql();
  }

  @Override
  public Period getLastPeriod() throws ServiceException {
    try {
      return this.periodDao.getLastPeriod();
    } catch (DaoException e) {
      logger.error("Cannot get last period.", e);
      throw new ServiceException("Cannot get last period.", e);
    }
  }

  @Override
  public Period save(Period period) throws ServiceException {
    try {
      return this.periodDao.save(period);
    } catch (DaoException e) {
      logger.error("Cannot save period", e);
      throw new ServiceException("Cannot save period", e);
    }
  }
}