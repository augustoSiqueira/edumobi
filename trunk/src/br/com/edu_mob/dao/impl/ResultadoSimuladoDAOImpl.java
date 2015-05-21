package br.com.edu_mob.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.comparator.ComparatorResultadoSimulado;
import br.com.edu_mob.dao.ResultadoSimuladoDAO;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.entity.infra.ResultadoSimuladoDTO;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Repository("resultadoSimuladoDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class ResultadoSimuladoDAOImpl extends GenericDAOImpl implements ResultadoSimuladoDAO {

	private static final Logger logger = Logger.getLogger(ResultadoSimuladoDAOImpl.class.getName());

	@Autowired
	public ResultadoSimuladoDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<ResultadoSimulado> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<ResultadoSimulado> listaResultadoSimulado = null;
		String idUsuario = filtro.getAsString("idUsuario");
		String idSimulado = filtro.getAsString("idSimulado");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ResultadoSimulado.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idSimulado != null) && !idSimulado.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("simulado.id", Long.parseLong(idSimulado)));
			}
			detachedCriteria.addOrder(Order.desc("id"));
			listaResultadoSimulado = this.findByCriteria(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimulado;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String idUsuario = filtro.getAsString("idUsuario");
		String idSimulado = filtro.getAsString("idSimulado");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ResultadoSimulado.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idSimulado != null) && !idSimulado.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("simulado.id", Long.parseLong(idSimulado)));
			}
			count = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<ResultadoSimulado> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize)
			throws DAOException {
		List<ResultadoSimulado> listaResultadoSimulado = null;
		String idUsuario = filtro.getAsString("idUsuario");
		String idSimulado = filtro.getAsString("idSimulado");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ResultadoSimulado.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idSimulado != null) && !idSimulado.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("simulado.id", Long.parseLong(idSimulado)));
			}
			detachedCriteria.addOrder(Order.desc("id"));
			listaResultadoSimulado = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimulado;
	}

	@Override
	public List<ResultadoSimuladoDTO> pesquisarRelatorioDesempenhoAlunos(Filter filtro) throws DAOException {
		List<ResultadoSimuladoDTO> listaResultadoSimuladoDTO = null;
		StringBuilder sb = new StringBuilder();
		String idSimulado = (String) filtro.get("idSimulado");
		try {
			sb.append("select new br.com.edu_mob.entity.infra.ResultadoSimuladoDTO(rs.usuario.nome, rs.qtdAcertos, rs.qtdErros) ");
			sb.append(" from ResultadoSimulado rs where rs.simulado.id = " + Long.parseLong(idSimulado) + " ");
			sb.append(" order by rs.qtdAcertos desc ");
			listaResultadoSimuladoDTO = this.getHibernateTemplate().execute(new HibernateCallback<List<ResultadoSimuladoDTO>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<ResultadoSimuladoDTO> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sb.toString());
					return query.list();
				}
			});
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimuladoDTO;
	}

	@Override
	public List<ResultadoSimuladoDTO> pesquisarRelatorioRankingAlunos(Filter filtro) throws DAOException {
		List<ResultadoSimuladoDTO> listaResultadoSimuladoDTO = new ArrayList<ResultadoSimuladoDTO>();
		List<Usuario> listaUsuarios = null;
		StringBuilder sbUsuarios = new StringBuilder();
		String idSimulado = (String) filtro.get("idSimulado");
		try {

			sbUsuarios.append(" select distinct rs.usuario from ResultadoSimulado rs where rs.simulado.id = " + idSimulado + " ");

			listaUsuarios = this.getHibernateTemplate().execute(new HibernateCallback<List<Usuario>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Usuario> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sbUsuarios.toString());
					return query.list();
				}
			});
			if((listaUsuarios != null) && !listaUsuarios.isEmpty()) {
				for (Usuario usuario : listaUsuarios) {
					StringBuilder sbResultadoSimulado = new StringBuilder();
					sbResultadoSimulado.append("select new br.com.edu_mob.entity.infra.ResultadoSimuladoDTO(rs.usuario.nome, rs.qtdAcertos, rs.qtdErros) ");
					sbResultadoSimulado.append(" from ResultadoSimulado rs where rs.simulado.id = " + Long.parseLong(idSimulado) + " ");
					sbResultadoSimulado.append(" and rs.usuario.id = " + usuario.getId() + " order by rs.qtdAcertos desc ");
					ResultadoSimuladoDTO resultadoSimuladoDTO = this.getHibernateTemplate().execute(new HibernateCallback<ResultadoSimuladoDTO>() {
						@Override
						public ResultadoSimuladoDTO doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(sbResultadoSimulado.toString());
							query.setMaxResults(1);
							if((query.list() != null) && !query.list().isEmpty()) {
								return (ResultadoSimuladoDTO) query.uniqueResult();
							}
							return null;
						}
					});
					if(resultadoSimuladoDTO != null) {
						listaResultadoSimuladoDTO.add(resultadoSimuladoDTO);
					}
				}
				Collections.sort(listaResultadoSimuladoDTO, new ComparatorResultadoSimulado());
			}
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimuladoDTO;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
