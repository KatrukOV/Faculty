package com.katruk.dao;

import com.katruk.exception.DaoException;
import com.katruk.entity.Model;

import java.util.Collection;

interface Dao<T extends Model> {

  Collection<T> getAll() throws DaoException;

  T get(long id) throws DaoException;

  void create(T value) throws DaoException;

  void remove(T value) throws DaoException;

  void update(T value) throws DaoException;

}
