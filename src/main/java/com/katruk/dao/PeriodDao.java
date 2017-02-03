package com.katruk.dao;

import com.katruk.entity.Period;
import com.katruk.exception.DaoException;

public interface PeriodDao {

  Period getLastPeriod() throws DaoException;

  Period save(final Period period) throws DaoException;
}
