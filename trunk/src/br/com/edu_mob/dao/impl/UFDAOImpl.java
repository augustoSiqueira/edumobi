package br.com.edu_mob.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.UFDAO;
import br.com.edu_mob.entity.UF;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Repository("uFDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class UFDAOImpl extends GenericDAOImpl implements UFDAO {

	private static final Logger logger = Logger.getLogger(UFDAOImpl.class.getName());

	@Autowired
	public UFDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<UF> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<UF> listaUfs = null;
		String sigla = filtro.getAsString("sigla");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UF.class);
			if((sigla != null) && !sigla.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("sigla", sigla));
			}
			detachedCriteria.addOrder(Order.asc("id"));
			listaUfs = this.findByCriteria(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUfs;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String sigla = filtro.getAsString("sigla");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UF.class);
			if((sigla != null) && !sigla.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("sigla", sigla));
			}
			detachedCriteria.addOrder(Order.asc("id"));
			count = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return count;
	}

	@Override
	public List<UF> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg,
			int paginaSize) throws DAOException {
		List<UF> listaUfs = null;
		String sigla = filtro.getAsString("sigla");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UF.class);
			if((sigla != null) && !sigla.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("sigla", sigla));
			}
			detachedCriteria.addOrder(Order.asc("id"));
			listaUfs = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUfs;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
