package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.controller.ResultadoSimuladoController;
import br.com.edu_mob.dao.ResultadoSimuladoDAO;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Service("resultadoSimuladoController")
public class ResultadoSimuladoControllerImpl implements ResultadoSimuladoController {

	private static final Logger logger = Logger.getLogger(ResultadoSimuladoControllerImpl.class.getName());

	@Autowired
	private ResultadoSimuladoDAO resultadoSimuladoDAO;

	@Override
	public List<ResultadoSimulado> listar() throws RNException {
		List<ResultadoSimulado> listaResultadoSimulado = null;
		try {
			listaResultadoSimulado = this.resultadoSimuladoDAO.findAll(ResultadoSimulado.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimulado;
	}

	@Override
	public ResultadoSimulado pesquisarPorId(Long id) throws RNException {
		ResultadoSimulado resultadoSimulado = null;
		try {
			resultadoSimulado = this.resultadoSimuladoDAO.findById(ResultadoSimulado.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return resultadoSimulado;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void incluir(ResultadoSimulado resultadoSimulado) throws RNException {
		try {
			this.resultadoSimuladoDAO.save(resultadoSimulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void alterar(ResultadoSimulado resultadoSimulado) throws RNException {
		try {
			this.resultadoSimuladoDAO.update(resultadoSimulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void excluir(ResultadoSimulado resultadoSimulado) throws RNException {
		try {
			this.resultadoSimuladoDAO.remove(resultadoSimulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<ResultadoSimulado> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<ResultadoSimulado> listaResultadoSimulado = null;
		try {
			listaResultadoSimulado = this.resultadoSimuladoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimulado;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.resultadoSimuladoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<ResultadoSimulado> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws RNException {
		List<ResultadoSimulado> listaResultadoSimulado = null;
		try {
			listaResultadoSimulado = this.resultadoSimuladoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaResultadoSimulado;
	}

}
