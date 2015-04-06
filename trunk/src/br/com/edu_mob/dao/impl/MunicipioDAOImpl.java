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

import br.com.edu_mob.dao.MunicipioDAO;
import br.com.edu_mob.entity.Municipio;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Repository("municipioDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class MunicipioDAOImpl extends GenericDAOImpl implements MunicipioDAO {

	private static final Logger logger = Logger.getLogger(MunicipioDAOImpl.class.getName());

	@Autowired
	public MunicipioDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Municipio> pesquisarPorFiltro(Filter filtro)
			throws DAOException {
		List<Municipio> listaMunicipios = null;
		String nome = filtro.getAsString("nome");
		String idUF = filtro.getAsString("idUF");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Municipio.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("nome", nome));
			}
			if((idUF != null) && !idUF.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("uf.id", Long.parseLong(idUF)));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaMunicipios = this.findByCriteria(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaMunicipios;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String nome = filtro.getAsString("nome");
		String idUF = filtro.getAsString("idUF");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Municipio.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("nome", nome));
			}
			if((idUF != null) && !idUF.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("uf.id", Long.parseLong(idUF)));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			count = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return count;
	}

	@Override
	public List<Municipio> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws DAOException {
		List<Municipio> listaMunicipios = null;
		String nome = filtro.getAsString("nome");
		String idUF = filtro.getAsString("idUF");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Municipio.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("nome", nome));
			}
			if((idUF != null) && !idUF.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("uf.id", Long.parseLong(idUF)));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaMunicipios = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaMunicipios;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
