package br.com.edu_mob.dao.impl;

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

import br.com.edu_mob.dao.AlternativaDAO;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.AlternativaDTO;
import br.com.edu_mob.util.Filter;


@Repository("alternativaDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class AlternativaDAOImpl extends GenericDAOImpl implements AlternativaDAO {

	private static final Logger logger = Logger.getLogger(AlternativaDAOImpl.class.getName());


	@Autowired
	public AlternativaDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Alternativa> incluirEmMemoria(Alternativa alternativa, List<Alternativa> lista){
		lista.add(alternativa);
		return lista;
	}

	@Override
	public Alternativa alterarEmMemoria(Alternativa alternativa){
		return alternativa;
	}

	@Override
	public List<Alternativa> excluirEmMemoria(Alternativa alternativa, List<Alternativa> lista){
		lista.remove(alternativa);
		return lista;
	}

	@Override
	public List<Alternativa> pesquisarPorFiltro(Filter filtro) throws DAOException {
		String resposta = filtro.getAsString("resposta");
		String idQuestao = (String) filtro.get("idQuestao");

		List<Alternativa> listaAlternativas = null;


		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Alternativa.class);
			if(resposta != null) {
				detachedCriteria.add(Restrictions.ilike("resposta", resposta, MatchMode.ANYWHERE));
			}

			if ((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}

			detachedCriteria.addOrder(Order.asc("id"));
			listaAlternativas = this.findByCriteria(detachedCriteria);

		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaAlternativas;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int retorno = 0;
		String resposta = filtro.getAsString("resposta");
		String idQuestao = (String) filtro.get("idQuestao");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Alternativa.class);
			if(resposta != null) {
				detachedCriteria.add(Restrictions.ilike("resposta", resposta));
			}

			if ((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}

			retorno = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Alternativa> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws DAOException {
		String resposta = filtro.getAsString("resposta");
		List<Alternativa> listaAlternativa = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Alternativa.class);
			if(resposta != null) {
				detachedCriteria.add(Restrictions.ilike("resposta", resposta));
			}

			detachedCriteria.addOrder(Order.asc("resposta"));
			listaAlternativa = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaAlternativa;
	}

	@Override
	public List<AlternativaDTO> pesquisarPorFiltroDTO(Filter filtro) throws DAOException {
		List<AlternativaDTO> listaAlternativasDTO = null;
		StringBuilder sb = new StringBuilder();
		String idQuestao = (String) filtro.get("idQuestao");
		try {
			sb.append("select new br.com.edu_mob.services.AlternativaDTO(a.id, a.resposta, a.correta, a.questao.id) from Alternativa a ");
			sb.append(" where a.questao.id = " + idQuestao + " ");
			listaAlternativasDTO = this.getHibernateTemplate().execute(new HibernateCallback<List<AlternativaDTO>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<AlternativaDTO> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(sb.toString());
					return query.list();
				}
			});
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaAlternativasDTO;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)	throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Alternativa.class);
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
