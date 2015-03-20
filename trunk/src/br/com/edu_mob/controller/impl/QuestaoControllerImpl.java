package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.dao.QuestaoDAO;
import br.com.edu_mob.dao.impl.QuestaoDAOImpl;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Service("questaoController")
public class QuestaoControllerImpl implements QuestaoController{

	private static final Logger logger = Logger.getLogger(QuestaoDAOImpl.class.getName());
	
	@Autowired
	private QuestaoDAO questaoDAO;

	@Override
	public List<Questao> listar() throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.findAll(Questao.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}
	
	@Override
	public Questao pesquisarPorId(Long id) throws RNException {
		Questao questao = null;
		try {
			questao = this.questaoDAO.findById(Questao.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return questao;
	}
	
	@Override
	public void incluir(Questao questao) throws RNException {
		try {
			this.questaoDAO.save(questao);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}
	
	@Override
	public void alterar(Questao questao) throws RNException {
		try {
			this.questaoDAO.update(questao);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}
	
	@Override
	public void excluir(Questao questao) throws RNException {
		Filter filtro = new Filter();
		try {
			filtro.put("id", questao.getId().toString());
			this.questaoDAO.remove(questao);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<Questao> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}
	
	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.questaoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Questao> pesquisarPorFiltroPaginada(Filter filtro,
		int primeiroReg, int paginaSize) throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}
	

}
