package br.com.edu_mob.dao.impl;

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

import br.com.edu_mob.dao.QuestaoDAO;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.QuestaoDTO;
import br.com.edu_mob.util.Filter;

@Repository("questaoDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class QuestaoDAOImpl extends GenericDAOImpl implements QuestaoDAO {

	private static final Logger logger = Logger.getLogger(QuestaoDAOImpl.class.getName());

	@Autowired
	public QuestaoDAOImpl(SessionFactory factory) {
		super(factory);
	}


	@Override
	public List<Questao> pesquisarPorFiltro(Filter filtro) throws DAOException {
		String enunciado = filtro.getAsString("enunciado");
		String idAreaConhecimento = filtro.getAsString("idAreaConhecimento");

		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		List<Questao> listaQuestoes = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);
			if(enunciado != null) {
				detachedCriteria.add(Restrictions.ilike("enunciado", enunciado, MatchMode.ANYWHERE));
			}

			if((idAreaConhecimento != null) && !idAreaConhecimento.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("areaConhecimento.id", Long.parseLong(idAreaConhecimento)));
			}

			if(dataAtualizacao != null) {
				detachedCriteria.add(Restrictions.ge("dataAtualizacao", dataAtualizacao));
			}

			detachedCriteria.addOrder(Order.desc("id"));
			listaQuestoes = this.findByCriteria(detachedCriteria);

		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int retorno = 0;
		String enunciado = filtro.getAsString("enunciado");
		String idCategoria = filtro.getAsString("idCategoria");
		String idAreaConhecimento = filtro.getAsString("idAreaConhecimento");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);

			if(enunciado != null) {
				detachedCriteria.add(Restrictions.ilike("enunciado", enunciado));
			}

			if((idCategoria != null) && !idCategoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(idCategoria)));
			}

			if((idAreaConhecimento != null) && !idAreaConhecimento.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("areaConhecimento.id", Long.parseLong(idAreaConhecimento)));
			}

			if(dataAtualizacao != null) {
				detachedCriteria.add(Restrictions.ge("dataAtualizacao", dataAtualizacao));
			}

			retorno = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Questao> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws DAOException {
		String enunciado = filtro.getAsString("enunciado");
		String idAreaConhecimento = filtro.getAsString("idAreaConhecimento");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		List<Questao> listaQuestao = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);
			if(enunciado != null) {
				detachedCriteria.add(Restrictions.ilike("enunciado", enunciado));
			}

			if((idAreaConhecimento != null) && !idAreaConhecimento.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("areaConhecimento.id", Long.parseLong(idAreaConhecimento)));
			}

			if(dataAtualizacao != null) {
				detachedCriteria.add(Restrictions.ge("dataAtualizacao", dataAtualizacao));
			}

			detachedCriteria.addOrder(Order.desc("id"));
			listaQuestao = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaQuestao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questao> pesquisarSimulado(Filter filtro) throws DAOException {
		List<Questao> listaQuestoes = null;
		List<AreaConhecimento> listaAreasConhecimento = (List<AreaConhecimento>) filtro.get("listaAreasConhecimento");
		String qtdQuestoes =  filtro.getAsString("qtdQuestoes");
		List<Long> listaIdsAreas = new ArrayList<Long>();
		StringBuilder sb = new StringBuilder();
		try {
			if((listaAreasConhecimento != null) && !listaAreasConhecimento.isEmpty()) {
				for (AreaConhecimento areaConhecimento : listaAreasConhecimento) {
					listaIdsAreas.add(areaConhecimento.getId());
				}
				sb.append("select q from Questao q ");
				sb.append(" where q.areaConhecimento.id in (:idsAreasConhecimento) ");
				listaQuestoes = this.getHibernateTemplate().execute(new HibernateCallback<List<Questao>>() {
					@SuppressWarnings("unchecked")
					@Override
					public List<Questao> doInHibernate(Session session) throws HibernateException {
						Query query = session.createQuery(sb.toString());
						query.setParameterList("idsAreasConhecimento", listaIdsAreas);
						query.setMaxResults(Integer.parseInt(qtdQuestoes));
						return query.list();
					}
				});
			}
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestaoDTO> pesquisarSimuladoDTO(Filter filtro) throws DAOException {
		List<QuestaoDTO> listaQuestoesDTO = null;
		List<AreaConhecimento> listaAreasConhecimento = (List<AreaConhecimento>) filtro.get("listaAreasConhecimento");
		List<Long> listaIdsAreas = new ArrayList<Long>();
		String qtdQuestoes =  filtro.getAsString("qtdQuestoes");
		StringBuilder sb = new StringBuilder();
		try {
			if((listaAreasConhecimento != null) && !listaAreasConhecimento.isEmpty()) {
				for (AreaConhecimento areaConhecimento : listaAreasConhecimento) {
					listaIdsAreas.add(areaConhecimento.getId());
				}
				sb.append("select new br.com.edu_mob.services.QuestaoDTO(q.id, q.enunciado, q.observacao, q.caminhoImagem, q.areaConhecimento.id, ");
				sb.append(" q.dataAtualizacao) from Questao q where q.areaConhecimento.id in (:idsAreasConhecimento) ");
				listaQuestoesDTO = this.getHibernateTemplate().execute(new HibernateCallback<List<QuestaoDTO>>() {
					@SuppressWarnings("unchecked")
					@Override
					public List<QuestaoDTO> doInHibernate(Session session) throws HibernateException {
						Query query = session.createQuery(sb.toString());
						query.setParameterList("idsAreasConhecimento", listaIdsAreas);
						query.setMaxResults(Integer.parseInt(qtdQuestoes));
						return query.list();
					}
				});
			}
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoesDTO;
	}

	@Override
	public int pesquisarQtdTotalQuestoes(Filter filtro) throws DAOException {
		int qtdTotalCategoria = 0;
		String idCategoria = filtro.getAsString("idCategoria");
		String idAreaConhecimento = filtro.getAsString("idAreaConhecimento");
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("select count(q.id) from Questao q ");
			
			if(idCategoria != null)
				sb.append(" where q.areaConhecimento.categoria.id = " + Long.parseLong(idCategoria) + " ");
			
			if(idAreaConhecimento != null)
				sb.append(" where q.areaConhecimento.id = " + Long.parseLong(idAreaConhecimento) + " ");
			
			qtdTotalCategoria = this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sb.toString());
					return Integer.parseInt(query.uniqueResult().toString());
				}
			});
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return qtdTotalCategoria;
	}

	@Override
	public List<Questao> pesquisarPorFiltroDTO(Filter filtro) throws DAOException {
		List<Questao> listaQuestoesDTO = null;
		StringBuilder sb = new StringBuilder();
		String idCategoria = (String) filtro.get("idCategoria");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		try {
			sb.append("select q ");
			sb.append("from Questao q where q.dataAtualizacao >= :dataAtualizacao and q.areaConhecimento.categoria.id = " + Long.parseLong(idCategoria) + " ");
			listaQuestoesDTO = this.getHibernateTemplate().execute(new HibernateCallback<List<Questao>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Questao> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sb.toString());
					query.setParameter("dataAtualizacao", dataAtualizacao);
					return query.list();
				}
			});
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoesDTO;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)	throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);
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
