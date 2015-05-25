package br.com.edu_mob.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.RespostaSimuladoDAO;
import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Repository("respostaSimuladoDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class RespostaSimuladoDAOImpl extends GenericDAOImpl implements RespostaSimuladoDAO {

	private static final Logger logger = Logger.getLogger(RespostaSimuladoDAOImpl.class.getName());

	@Autowired
	public RespostaSimuladoDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public void salvar(List<RespostaSimulado> listaRespostaSimulado, ResultadoSimulado resultadoSimulado) throws DAOException {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			session.save(resultadoSimulado);
			for (RespostaSimulado respostaSimulado : listaRespostaSimulado) {
				respostaSimulado.setResultadoSimulado(resultadoSimulado);
				session.save(respostaSimulado);
			}
			session.getTransaction().commit();
		} catch(DataAccessException e) {
			session.getTransaction().rollback();
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		} finally {
			if(session != null) {
				session.close();
			}
		}

	}

	@Override
	public List<RespostaSimulado> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<RespostaSimulado> listaRespostaSimulado = null;
		String idUsuario = filtro.getAsString("idUsuario");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RespostaSimulado.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}
			if(correta != null) {
				detachedCriteria.add(Restrictions.eq("correta", correta));
			}
			listaRespostaSimulado = this.findByCriteria(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaSimulado;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String idUsuario = filtro.getAsString("idUsuario");
		String idCategoria = filtro.getAsString("idCategoria");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RespostaSimulado.class);
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
	public List<RespostaSimulado> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize)
			throws DAOException {
		List<RespostaSimulado> listaRespostaSimulado = null;
		String idUsuario = filtro.getAsString("idUsuario");
		String idQuestao = filtro.getAsString("idQuestao");
		Boolean correta = (Boolean) filtro.get("correta");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RespostaSimulado.class);
			if((idUsuario != null) && !idUsuario.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("usuario.id", Long.parseLong(idUsuario)));
			}
			if((idQuestao != null) && !idQuestao.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("questao.id", Long.parseLong(idQuestao)));
			}
			if(correta != null) {
				detachedCriteria.add(Restrictions.eq("correta", correta));
			}
			listaRespostaSimulado = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaSimulado;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
