package br.com.edu_mob.controller.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.controller.RespostaEstudoController;
import br.com.edu_mob.dao.RespostaEstudoDAO;
import br.com.edu_mob.entity.RespostaEstudo;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Service("respostaEstudoController")
public class RespostaEstudoControllerImpl implements RespostaEstudoController {

	private static final Logger logger = Logger.getLogger(RespostaEstudoControllerImpl.class.getName());

	@Autowired
	private RespostaEstudoDAO respostaEstudoDAO;

	@Override
	public List<RespostaEstudo> listar() throws RNException {
		List<RespostaEstudo> listaRespostaEstudo = null;
		try {
			listaRespostaEstudo = this.respostaEstudoDAO.findAll(RespostaEstudo.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaEstudo;
	}

	@Override
	public RespostaEstudo pesquisarPorId(Long id) throws RNException {
		RespostaEstudo respostaEstudo = null;
		try {
			respostaEstudo = this.respostaEstudoDAO.findById(RespostaEstudo.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return respostaEstudo;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void salvar(RespostaEstudo respostaEstudo) throws RNException {
		List<RespostaEstudo> listaRespostaResult = null;
		Filter filtro = new Filter();
		try {
			filtro.put("idUsuario", respostaEstudo.getUsuario().getId().toString());
			filtro.put("idQuestao", respostaEstudo.getQuestao().getId().toString());
			listaRespostaResult = this.respostaEstudoDAO.pesquisarPorFiltro(filtro);
			if((listaRespostaResult != null) && !listaRespostaResult.isEmpty()) {
				listaRespostaResult.get(0).setCorreta(respostaEstudo.isCorreta());
				listaRespostaResult.get(0).setDataHora(new Date());
				this.respostaEstudoDAO.update(listaRespostaResult.get(0));
			} else {
				this.respostaEstudoDAO.save(respostaEstudo);
			}
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void incluir(RespostaEstudo respostaEstudo) throws RNException {
		try {
			this.respostaEstudoDAO.save(respostaEstudo);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void alterar(RespostaEstudo respostaEstudo) throws RNException {
		try {
			this.respostaEstudoDAO.update(respostaEstudo);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void excluir(RespostaEstudo respostaEstudo) throws RNException {
		try {
			this.respostaEstudoDAO.remove(respostaEstudo);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<RespostaEstudo> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<RespostaEstudo> listaRespostaEstudo = null;
		try {
			listaRespostaEstudo = this.respostaEstudoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaEstudo;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.respostaEstudoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<RespostaEstudo> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws RNException {
		List<RespostaEstudo> listaRespostaEstudo = null;
		try {
			listaRespostaEstudo = this.respostaEstudoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaEstudo;
	}

	@Override
	public int pesquisarPorFiltroCountHQLRelatorio(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.respostaEstudoDAO.pesquisarPorFiltroCountHQLRelatorio(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

}
