package br.com.edu_mob.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.PerfilDAO;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Repository("perfilDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class PerfilDAOImpl extends GenericDAOImpl implements PerfilDAO {

	private static final Logger logger = Logger.getLogger(PerfilDAOImpl.class.getName());

	@Autowired
	public PerfilDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Perfil> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<Perfil> listaPerfis = null;
		String nome = filtro.getAsString("nome");
		Boolean ativo = (Boolean) filtro.get("ativo");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Perfil.class);
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if (ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaPerfis = this.findByCriteria(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaPerfis;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String nome = filtro.getAsString("nome");
		Boolean ativo = (Boolean) filtro.get("ativo");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Perfil.class);
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if (ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			count = this.getDataCount(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<Perfil> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws DAOException {
		List<Perfil> listaPerfis = null;
		String nome = filtro.getAsString("nome");
		Boolean ativo = (Boolean) filtro.get("ativo");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Perfil.class);
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if (ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaPerfis = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaPerfis;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Perfil.class);
				detachedCriteria.add(Restrictions.eq(campo, valor).ignoreCase());
				if (id != null) {
					detachedCriteria.add(Restrictions.ne("id", id));
				}
				existe = !this.findByCriteria(detachedCriteria).isEmpty();
			}
			return existe;
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
	}
}
