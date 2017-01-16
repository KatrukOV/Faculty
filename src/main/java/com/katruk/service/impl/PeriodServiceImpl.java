package com.katruk.service.impl;

import com.katruk.dao.PeriodDao;
import com.katruk.dao.mysql.PeriodDaoMySql;
import com.katruk.entity.Period;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.PeriodService;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;

public final class PeriodServiceImpl implements PeriodService {

  private final Logger logger;
  private final PeriodDao periodDao;

  public PeriodServiceImpl() {
    this.logger = Logger.getLogger(PeriodServiceImpl.class);
    this.periodDao = new PeriodDaoMySql();
  }

  @Override
  public Period getLastPeriod() throws ServiceException {
    final Period period;
    try {
      period = this.periodDao.getLastPeriod()
          .orElseThrow(() -> new DaoException("Period not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return period;
  }

  @Override
  public Period save(Period period) throws ServiceException {
    try {
      period = this.periodDao.save(period);
    } catch (DaoException e) {
      logger.error("cant save period", e);
      throw new ServiceException("cant save period", e);
    }
    return period;
  }
}