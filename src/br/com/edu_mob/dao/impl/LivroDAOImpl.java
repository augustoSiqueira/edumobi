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

import br.com.edu_mob.dao.LivroDAO;
import br.com.edu_mob.entity.Livro;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Repository("livroDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LivroDAOImpl extends GenericDAOImpl implements LivroDAO,
		Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LivroDAOImpl.class
			.getName());

	@Autowired
	public LivroDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Livro> pesquisarPorFiltro(Filter filtro) throws DAOException {
		String nome = filtro.getAsString("nome");
		String descricao = filtro.getAsString("descricao");
		String capa = filtro.getAsString("capa");
		String isbn = filtro.getAsString("isbn");
		String edicao = filtro.getAsString("edicao");
		String categoria = filtro.getAsString("idCategoria");
		String web = filtro.getAsString("web");
		
		List<Livro> listaLivros = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Livro.class);
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome,
						MatchMode.ANYWHERE));
			}

			if (descricao != null) {
				detachedCriteria.add(Restrictions.like("descricao", "%"+descricao+"%"));
			}

			if (capa != null) {
				detachedCriteria.add(Restrictions.eq("capa", capa));
			}

			if (isbn != null) {
				detachedCriteria.add(Restrictions.eq("isbn", isbn));
			}

			if (edicao != null) {
				detachedCriteria.add(Restrictions.eq("edicao", edicao));
			}

			if((categoria != null) && !categoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
			}

			if (web != null) {
				detachedCriteria.add(Restrictions.or(Restrictions.like("descricao", "%"+web+"%"), Restrictions.like("nome", "%"+web+"%")));
			}
			
			detachedCriteria.addOrder(Order.asc("nome"));
			listaLivros = this.findByCriteria(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaLivros;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int retorno = 0;
		String nome = filtro.getAsString("nome");
		String descricao = filtro.getAsString("descricao");
		String capa = filtro.getAsString("capa");
		String isbn = filtro.getAsString("isbn");
		String edicao = filtro.getAsString("edicao");
		String categoria = filtro.getAsString("idCategoria");
		
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Livro.class);
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome,
						MatchMode.ANYWHERE));
			}

			if (descricao != null) {
				detachedCriteria.add(Restrictions.eq("descricao", descricao));
			}

			if (capa != null) {
				detachedCriteria.add(Restrictions.eq("capa", capa));
			}

			if (isbn != null) {
				detachedCriteria.add(Restrictions.eq("isbn", isbn));
			}

			if (edicao != null) {
				detachedCriteria.add(Restrictions.eq("edicao", edicao));
			}
			
			if((categoria != null) && !categoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
			}

			retorno = this.getDataCount(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return retorno;
		
	}

	@Override
	public List<Livro> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws DAOException {
		
		String nome = filtro.getAsString("nome");
		String descricao = filtro.getAsString("descricao");
		String capa = filtro.getAsString("capa");
		String isbn = filtro.getAsString("isbn");
		String edicao = filtro.getAsString("edicao");
		String categoria = filtro.getAsString("idCategoria");

		List<Livro> listaLivros = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Livro.class);
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome,
						MatchMode.ANYWHERE));
			}

			if (descricao != null) {
				detachedCriteria.add(Restrictions.eq("descricao", descricao));
			}

			if (capa != null) {
				detachedCriteria.add(Restrictions.eq("capa", capa));
			}

			if (isbn != null) {
				detachedCriteria.add(Restrictions.eq("isbn", isbn));
			}

			if (edicao != null) {
				detachedCriteria.add(Restrictions.eq("edicao", edicao));
			}

			if((categoria != null) && !categoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
			}
			
			detachedCriteria.addOrder(Order.asc("nome"));
			listaLivros = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaLivros;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Livro.class);
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
