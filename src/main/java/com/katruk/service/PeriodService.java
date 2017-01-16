package com.katruk.service;

import com.katruk.entity.Period;
import com.katruk.exception.ServiceException;

public interface PeriodService {

  Period getLastPeriod() throws ServiceException;

  Period save(final Period period) throws ServiceException;
}
