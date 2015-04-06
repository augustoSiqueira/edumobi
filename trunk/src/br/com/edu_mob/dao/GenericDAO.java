package br.com.edu_mob.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

public interface GenericDAO {

	<T> List<T> findAll(Class<T> entityClass) throws DataAccessException;

	<T> T findById(Class<T> entityClass, Long id) throws DataAccessException;

	<T> void saveOrUpdate(T entity) throws DataAccessException;

	<T> void save(T entity) throws DataAccessException;

	<T> void update(T entity) throws DataAccessException;

	<T> T merge(T entity) throws DataAccessException;

	<T> void remove(T entity) throws DataAccessException;

	<T> void removeAll(Class<T> entityClass) throws DataAccessException;

	<T> List<T> findByNamedQueryAndNamedParam(Class<T> entityClass, String queryName, String[] paramNames, Object[] values)
			throws DataAccessException;

	<T> List<T> findByNamedQueryAndNamedParam(Class<T> entityClass, String queryName, Map<String, ?> params)
			throws DataAccessException;

	<T> List<T> findByNamedParam(Class<T> entityClass, String query, String[] paramNames, Object[] values)
			throws DataAccessException;

	<T> List<T> findByNamedParam(Class<T> entityClass, String query, Map<String, ?> params) throws DataAccessException;

	<T> List<T> findByCriteria(DetachedCriteria criteria) throws DataAccessException;

	<T> List<T> buscarPaginada(DetachedCriteria criteria, int primeiroReg, int paginaSize) throws DataAccessException;

}
