package br.com.edu_mob.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.SimuladoDescricaoDAO;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.SimuladoDTO;
import br.com.edu_mob.util.Filter;

@Repository("simuladoDescricaoDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class SimuladoDescricaoDAOImpl extends GenericDAOImpl implements
SimuladoDescricaoDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SimuladoDescricaoDAOImpl.class
			.getName());

	@Autowired
	public SimuladoDescricaoDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Simulado> pesquisarPorFiltro(Filter filtro)
			throws DAOException {
		String titulo = filtro.getAsString("titulo");
		String descricao = filtro.getAsString("descricao");
		Date tempo = (Date) filtro.get("tempo");
		String categoria = filtro.getAsString("idCategoria");

		List<Simulado> listaSimulado = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Simulado.class);
			if ((titulo != null) && !titulo.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("titulo", titulo,
						MatchMode.ANYWHERE));
			}

			if (descricao != null) {
				detachedCriteria.add(Restrictions.eq("descricao", descricao));
			}

			if (tempo != null) {
				detachedCriteria.add(Restrictions.eq("tempo", tempo));
			}

			if((categoria != null) && !categoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
			}


			detachedCriteria.addOrder(Order.asc("titulo"));
			listaSimulado = this.findByCriteria(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaSimulado;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		String titulo = filtro.getAsString("titulo");
		String descricao = filtro.getAsString("descricao");
		Date tempo = (Date) filtro.get("tempo");
		String categoria = filtro.getAsString("idCategoria");

		int retorno = 0;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Simulado.class);
			if ((titulo != null) && !titulo.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("titulo", titulo,
						MatchMode.ANYWHERE));
			}

			if (descricao != null) {
				detachedCriteria.add(Restrictions.eq("descricao", descricao));
			}

			if (tempo != null) {
				detachedCriteria.add(Restrictions.eq("tempo", tempo));
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
	public List<Simulado> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws DAOException {
		String titulo = filtro.getAsString("titulo");
		String descricao = filtro.getAsString("descricao");
		Date tempo = (Date) filtro.get("tempo");
		String categoria = filtro.getAsString("idCategoria");

		List<Simulado> listaSimulado = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Simulado.class);
			if ((titulo != null) && !titulo.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("titulo", titulo,
						MatchMode.ANYWHERE));
			}

			if (descricao != null) {
				detachedCriteria.add(Restrictions.eq("descricao", descricao));
			}

			if (tempo != null) {
				detachedCriteria.add(Restrictions.eq("tempo", tempo));
			}

			if((categoria != null) && !categoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
			}


			detachedCriteria.addOrder(Order.asc("titulo"));
			listaSimulado = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaSimulado;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Simulado.class);
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

	@Override
	public List<SimuladoDTO> pesquisarPorFiltroDTO(Filter filtro) throws DAOException {
		List<SimuladoDTO> listaSimuladoDTO = new ArrayList<SimuladoDTO>();
		List<Simulado> listaSimulado = null;
		StringBuilder sb = new StringBuilder();
		String idCategoria = filtro.getAsString("idCategoria");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		try {
			sb.append("select s from Simulado s where s.dataAtualizacao >= :dataAtualizacao and s.categoria.id = " + Long.parseLong(idCategoria) + " ");
			listaSimulado = this.getHibernateTemplate().execute(new HibernateCallback<List<Simulado>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Simulado> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sb.toString());
					query.setParameter("dataAtualizacao", dataAtualizacao);
					return query.list();
				}
			});
			if((listaSimulado != null) && !listaSimulado.isEmpty()) {
				for (int i = 0; i < listaSimulado.size(); i++) {
					SimuladoDTO simuladoDTO = new SimuladoDTO(listaSimulado.get(i).getId(), listaSimulado.get(i).getTitulo(),
							listaSimulado.get(i).getDescricao(), listaSimulado.get(i).getDuracao(), listaSimulado.get(i).getQntQuestao(),
							listaSimulado.get(i).getCategoria(), listaSimulado.get(i).getAreasConhecimento());
					listaSimulado.remove(listaSimulado.get(i));
					listaSimuladoDTO.add(simuladoDTO);
				}
			}
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaSimuladoDTO;
	}

}
