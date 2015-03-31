package br.com.edu_mob.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.AreaConhecimentoDAO;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Repository("areaConhecimentoDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class AreaConhecimentoDAOImpl extends GenericDAOImpl implements AreaConhecimentoDAO, Serializable {

	private static final long serialVersionUID = -539985070564944663L;
	private static final Logger logger = Logger.getLogger(AreaConhecimentoDAOImpl.class.getName());

	@Autowired
	public AreaConhecimentoDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<AreaConhecimento> pesquisarPorFiltro(Filter filtro)	throws DAOException {

		List<AreaConhecimento> listaAreaConhecimento = null;
		String descricao = filtro.getAsString("descricao");
		String categoria = filtro.getAsString("idCategoria");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AreaConhecimento.class);

			if ((descricao != null) && !descricao.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
			}

			if((categoria != null) && !categoria.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
			}

			if(dataAtualizacao != null) {
				detachedCriteria.add(Restrictions.ge("dataAtualizacao", dataAtualizacao));
			}

			listaAreaConhecimento = this.findByCriteria(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaAreaConhecimento;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int retorno = -1;

		String descricao = filtro.getAsString("descricao");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AreaConhecimento.class);

			if ((descricao != null) && !descricao.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
			}

			if(dataAtualizacao != null) {
				detachedCriteria.add(Restrictions.ge("dataAtualizacao", dataAtualizacao));
			}

			retorno = this.getDataCount(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return retorno;
	}

	@Override
	public List<AreaConhecimento> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws DAOException {

		List<AreaConhecimento> listaAreaConhecimento = null;
		String descricao = filtro.getAsString("descricao");
		Date dataAtualizacao = (Date) filtro.get("dataAtualizacao");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AreaConhecimento.class);
			if ((descricao != null) && !descricao.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
			}

			if(dataAtualizacao != null) {
				detachedCriteria.add(Restrictions.ge("dataAtualizacao", dataAtualizacao));
			}

			listaAreaConhecimento = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);

		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}

		return listaAreaConhecimento;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AreaConhecimento.class);
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

	@Override
	public List<AreaConhecimento> incluirEmMemoria(
			AreaConhecimento areaConhecimento, List<AreaConhecimento> lista) {
		lista.add(areaConhecimento);
		return lista;
	}

	@Override
	public AreaConhecimento alterarEmMemoria(AreaConhecimento areaConhecimento) {
		return areaConhecimento;
	}

	@Override
	public List<AreaConhecimento> excluirEmMemoria(
			AreaConhecimento areaConhecimento, List<AreaConhecimento> lista) {
		lista.remove(areaConhecimento);
		return lista;
	}

}
