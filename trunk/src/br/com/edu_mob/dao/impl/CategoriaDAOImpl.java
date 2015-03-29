package br.com.edu_mob.dao.impl;

import java.io.Serializable;
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

import br.com.edu_mob.dao.CategoriaDAO;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Repository("categoriaDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class CategoriaDAOImpl extends GenericDAOImpl implements CategoriaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CategoriaDAOImpl.class.getName());

	@Autowired
	public CategoriaDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Categoria> pesquisarPorFiltro(Filter filtro)
			throws DAOException {
		String nome = filtro.getAsString("nome");
		Boolean ativo = (Boolean) filtro.get("ativo");
		String idPai = filtro.getAsString("idPai");
		Boolean curso = (Boolean) filtro.get("curso");
		List<Categoria> listaCategorias = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Categoria.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if(ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			if((idPai != null) && !idPai.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("pai.id", Long.parseLong(idPai)));
			}
			if(curso != null) {
				detachedCriteria.add(Restrictions.eq("curso", curso));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaCategorias = this.findByCriteria(detachedCriteria);

		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaCategorias;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int retorno = 0;
		String nome = filtro.getAsString("nome");
		Boolean ativo = (Boolean) filtro.get("ativo");
		String idPai = filtro.getAsString("idPai");
		Boolean curso = (Boolean) filtro.get("curso");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Categoria.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if(ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			if((idPai != null) && !idPai.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("pai.id", Long.parseLong(idPai)));
			}
			if(curso != null) {
				detachedCriteria.add(Restrictions.eq("curso", curso));
			}
			retorno = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Categoria> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws DAOException {
		String nome = filtro.getAsString("nome");
		Boolean ativo = (Boolean) filtro.get("ativo");
		Boolean curso = (Boolean) filtro.get("curso");
		String idPai = filtro.getAsString("idPai");
		List<Categoria> listaCategorias = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Categoria.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if(ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			if((idPai != null) && !idPai.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("pai.id", Long.parseLong(idPai)));
			}
			if(curso != null) {
				detachedCriteria.add(Restrictions.eq("curso", curso));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaCategorias = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaCategorias;
	}

	@Override
	public int pesquisarDependencia(Filter filtro) throws DAOException {
		int retorno = 0;
		String id = filtro.getAsString("id");
		Boolean ativo = (Boolean) filtro.get("ativo");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Categoria.class);
			if(ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			if((id != null) && !id.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("pai.id", Long.parseLong(id)));
			}
			retorno = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Categoria.class);
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
