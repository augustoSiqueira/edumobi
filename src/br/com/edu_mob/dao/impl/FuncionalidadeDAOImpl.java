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

import br.com.edu_mob.dao.FuncionalidadeDAO;
import br.com.edu_mob.entity.Funcionalidade;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Repository("funcionalidadeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class FuncionalidadeDAOImpl extends GenericDAOImpl implements FuncionalidadeDAO {

	private static final Logger logger = Logger.getLogger(FuncionalidadeDAOImpl.class.getName());

	@Autowired
	public FuncionalidadeDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Funcionalidade> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<Funcionalidade> listaFuncionalidades = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Funcionalidade.class);
			detachedCriteria.add(Restrictions.eq("exibir", Boolean.TRUE));
			detachedCriteria.addOrder(Order.asc("id"));
			listaFuncionalidades = this.findByCriteria(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaFuncionalidades;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Funcionalidade> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
