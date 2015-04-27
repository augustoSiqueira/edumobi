package br.com.edu_mob.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.RespostaEstudoDAO;
import br.com.edu_mob.entity.RespostaEstudo;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.Util;

@Repository("respostaEstudoDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class RespostaEstudoDAOImpl extends GenericDAOImpl implements RespostaEstudoDAO {

	private static final Logger logger = Logger.getLogger(RespostaEstudoDAOImpl.class.getName());

	@Autowired
	public RespostaEstudoDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<RespostaEstudo> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<RespostaEstudo> listaRespostaEstudo = null;
		String idUsuario = filtro.getAsString("idUsuario");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RespostaEstudo.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}
			if(correta != null) {
				detachedCriteria.add(Restrictions.eq("correta", correta));
			}
			listaRespostaEstudo = this.findByCriteria(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaEstudo;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String idUsuario = filtro.getAsString("idUsuario");
		String idCategoria = filtro.getAsString("idCategoria");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RespostaEstudo.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idCategoria != null) && !idCategoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("areaConhecimento.categoria.id", Long.parseLong(idCategoria)));
			}
			if((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}
			if(correta != null) {
				detachedCriteria.add(Restrictions.eq("correta", correta));
			}
			count = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<RespostaEstudo> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize)
			throws DAOException {
		List<RespostaEstudo> listaRespostaEstudo = null;
		String idUsuario = filtro.getAsString("idUsuario");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RespostaEstudo.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}
			if(correta != null) {
				detachedCriteria.add(Restrictions.eq("correta", correta));
			}
			listaRespostaEstudo = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaEstudo;
	}

	@Override
	public int pesquisarPorFiltroCountHQLRelatorio(Filter filtro) throws DAOException {
		int count = 0;
		String idUsuario = filtro.getAsString("idUsuario");
		String idCategoria = filtro.getAsString("idCategoria");
		String idAreaConhecimento = filtro.getAsString("idAreaConhecimento");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("select count(re.id) from RespostaEstudo re ");

			if((idUsuario != null) && !idUsuario.isEmpty()) {
				sb.append(" where re.usuario.id = " + Long.parseLong(idUsuario) + " ");
			}

			if((idCategoria != null) && !idCategoria.isEmpty()) {
				if(!Util.verificarWhere(sb.toString())) {
					sb.append(" and re.areaConhecimento.categoria.id = " + Long.parseLong(idCategoria) + " ");
				} else {
					sb.append(" where re.areaConhecimento.categoria.id = " + Long.parseLong(idCategoria) + " ");
				}
			}

			if((idAreaConhecimento != null) && !idAreaConhecimento.isEmpty()) {
				if(!Util.verificarWhere(sb.toString())) {
					sb.append(" and re.areaConhecimento.id = " + Long.parseLong(idAreaConhecimento) + " ");
				} else {
					sb.append(" where re.areaConhecimento.id = " + Long.parseLong(idAreaConhecimento) + " ");
				}
			}

			if((idQuestao != null) && !idQuestao.isEmpty()) {
				if(!Util.verificarWhere(sb.toString())) {
					sb.append(" and re.questao.id = " + Long.parseLong(idQuestao) + " ");
				} else {
					sb.append(" where re.questao.id = " + Long.parseLong(idQuestao) + " ");
				}
			}

			if(correta != null) {
				if(!Util.verificarWhere(sb.toString())) {
					sb.append(" and re.correta = :correta ");
				} else {
					sb.append(" where re.correta = :correta ");
				}
			}
			count = this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sb.toString());
					if(correta != null) {
						query.setParameter("correta", correta);
					}
					return Integer.parseInt(query.uniqueResult().toString());
				}

			});
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
