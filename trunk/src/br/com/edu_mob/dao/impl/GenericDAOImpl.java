package br.com.edu_mob.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.GenericDAO;

@Repository("genericDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class GenericDAOImpl implements GenericDAO {

	private HibernateTemplate hibernateTemplate;

	private static final Logger logger = Logger.getLogger(GenericDAOImpl.class.getName());

	@Autowired
	public GenericDAOImpl(SessionFactory factory) {
		this.hibernateTemplate = new HibernateTemplate(factory);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) throws DataAccessException {

		this.hibernateTemplate.setCacheQueries(true);
		List<T> results = this.hibernateTemplate.loadAll(entityClass);
		Set<T> set = new HashSet<T>(results);
		results = new ArrayList<T>(set);
		return results;

	}

	@Override
	public <T> T findById(Class<T> entityClass, Long id) throws DataAccessException {
		this.hibernateTemplate.setCacheQueries(true);
		T object = this.hibernateTemplate.get(entityClass, id);
		if (object == null) {
			throw new ObjectRetrievalFailureException(entityClass, id);
		} else {
			return object;
		}
	}

	@Override
	public <T> void saveOrUpdate(T entity) throws DataAccessException {
		this.hibernateTemplate.saveOrUpdate(entity);
	}

	@Override
	public <T> void save(T entity) throws DataAccessException {
		this.hibernateTemplate.save(entity);
	}

	@Override
	public <T> void update(T entity) throws DataAccessException {
		this.hibernateTemplate.update(entity);
	}

	@Override
	public <T> T merge(T entity) throws DataAccessException {
		return this.hibernateTemplate.merge(entity);
	}

	@Override
	public <T> void remove(T entity) throws DataAccessException {
		this.hibernateTemplate.delete(entity);
	}

	@Override
	public <T> void removeAll(Class<T> entityClass) throws DataAccessException {
		this.hibernateTemplate.deleteAll(this.findAll(entityClass));
	}

	@Override
	public <T> List<T> findByNamedQueryAndNamedParam(Class<T> entityClass, String queryName, String[] paramNames,
			Object[] values) throws DataAccessException {

		List<T> results = (List<T>) this.hibernateTemplate.findByNamedQueryAndNamedParam(queryName, paramNames, values);
		return results;
	}

	@Override
	public <T> List<T> findByNamedQueryAndNamedParam(Class<T> entityClass, String queryName, Map<String, ?> params)
			throws DataAccessException {

		String[] paramNames = new String[params.size()];
		Object[] values = new Object[params.size()];

		List<String> keys = new ArrayList<String>(params.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			paramNames[i] = key;
			values[i] = params.get(key);
		}

		return this.findByNamedQueryAndNamedParam(entityClass, queryName, paramNames, values);
	}

	@Override
	public <T> List<T> findByNamedParam(Class<T> entityClass, String query, String[] paramNames, Object[] values)
			throws DataAccessException {

		List<T> results = (List<T>) this.hibernateTemplate.findByNamedParam(query, paramNames, values);
		return results;

	}

	@Override
	public <T> List<T> findByNamedParam(Class<T> entityClass, String query, Map<String, ?> params)
			throws DataAccessException {

		String[] paramNames = new String[params.size()];
		Object[] values = new Object[params.size()];

		List<String> keys = new ArrayList<String>(params.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			paramNames[i] = key;
			values[i] = params.get(key);
		}

		List<T> results = (List<T>) this.hibernateTemplate.findByNamedParam(query, paramNames, values);
		return results;

	}

	@Override
	public <T> List<T> findByCriteria(DetachedCriteria criteria) throws DataAccessException {
		return (List<T>) this.hibernateTemplate.findByCriteria(criteria);
	}

	@Override
	public <T> List<T> buscarPaginada(DetachedCriteria criteria, int primeiroReg, int paginaSize)
			throws DataAccessException {
		return (List<T>) this.hibernateTemplate.findByCriteria(criteria, primeiroReg, paginaSize);
	}

	public int getDataCount(DetachedCriteria criteria) {
		return this.hibernateTemplate.findByCriteria(criteria).size();
	}

	protected HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

}
