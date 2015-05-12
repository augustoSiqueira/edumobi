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

import br.com.edu_mob.dao.ResultadoSimuladoDAO;
import br.com.edu_mob.entity.ResultadoSimulado;
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
			detachedCriteria.addOrder(Order.asc("id"));
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
			listaResultadoSimulado = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimulado;

	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
