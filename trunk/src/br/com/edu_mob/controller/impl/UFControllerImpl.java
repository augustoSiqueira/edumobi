package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.UFController;
import br.com.edu_mob.dao.UFDAO;
import br.com.edu_mob.entity.UF;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Service("uFController")
public class UFControllerImpl implements UFController {

	private static final Logger logger = Logger.getLogger(UFControllerImpl.class.getName());

	@Autowired
	private UFDAO ufDAO;

	@Override
	public List<UF> listar() throws RNException {
		List<UF> listaUF = null;
		try {
			listaUF = this.ufDAO.findAll(UF.class);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUF;
	}

	@Override
	public UF pesquisarPorId(Long id) throws RNException {
		UF uf = null;
		try {
			uf = this.ufDAO.findById(UF.class, id);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return uf;
	}

	@Override
	public void incluir(UF t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(UF t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(UF t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UF> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<UF> listaUF = null;
		try {
			listaUF = this.ufDAO.pesquisarPorFiltro(filtro);
		} catch(DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUF;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.ufDAO.pesquisarPorFiltroCount(filtro);
		} catch(DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return count;
	}

	@Override
	public List<UF> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<UF> listaUF = null;
		try {
			listaUF = this.ufDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch(DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUF;
	}

}
