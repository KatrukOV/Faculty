package com.katruk.dao;

import com.katruk.entity.Period;
import com.katruk.exception.DaoException;

import java.util.Optional;

public interface PeriodDao {

  Optional<Period> getPeriodById(final Long periodId) throws DaoException;

  Optional<Period> getLastPeriod() throws DaoException;

  Period save(final Period period) throws DaoException;
}
