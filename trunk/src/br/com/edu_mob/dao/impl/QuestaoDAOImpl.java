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

import br.com.edu_mob.dao.QuestaoDAO;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Repository("QuestaoDAO")
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
		List<Questao> listaQuestoes = null;
		

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);
			if(enunciado != null) {
				detachedCriteria.add(Restrictions.ilike("enunciado", enunciado, MatchMode.ANYWHERE));
			}
			
			detachedCriteria.addOrder(Order.asc("id"));
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
		
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);
			if(enunciado != null) {
				detachedCriteria.add(Restrictions.ilike("enunciado", enunciado));
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
		List<Questao> listaQuestao = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questao.class);
			if(enunciado != null) {
				detachedCriteria.add(Restrictions.ilike("enunciado", enunciado));
			}
			
			detachedCriteria.addOrder(Order.asc("enunciado"));
			listaQuestao = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaQuestao;
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
