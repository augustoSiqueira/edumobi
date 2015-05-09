package br.com.edu_mob.controller.impl;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.SimuladoDescricaoController;
import br.com.edu_mob.dao.SimuladoDescricaoDAO;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.SimuladoDTO;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Service("simuladoDescricaoController")
public class SimuladoDescricaoControllerImpl implements SimuladoDescricaoController, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(SimuladoDescricaoControllerImpl.class.getName());

	@Autowired
	SimuladoDescricaoDAO simuladoDescricaoDAO;

	@Override
	public List<Simulado> listar() throws RNException {

		List<Simulado> listaSimulado = null;
		try {
			listaSimulado = this.simuladoDescricaoDAO
					.findAll(Simulado.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO
					.getChave()));
		}
		return listaSimulado;

	}

	@Override
	public Simulado pesquisarPorId(Long id) throws RNException {
		Simulado simulado = null;
		try {
			simulado = this.simuladoDescricaoDAO.findById(Simulado.class,
					id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO
					.getChave()));
		}
		return simulado;
	}

	@Override
	public void incluir(Simulado simulado) throws RNException {
		try {
			this.simuladoDescricaoDAO.save(simulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO
					.getChave()));
		}
	}

	@Override
	public void alterar(Simulado simulado) throws RNException {
		try {
			this.simuladoDescricaoDAO.update(simulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO
					.getChave()));
		}
	}

	//valida se existe um outro simulado com o mesmo titulo e mesma categoria
	//pode ter titulos iguais, contanto que a categoria seja diferente
	//Esta incompleta.
	private void validarTitulo(Simulado simulado){
		List<Simulado> listaSimulado = null;
		Filter filtro = new Filter();
		filtro.put("", simulado.getTitulo());
		filtro.put("", simulado.getCategoria().getId());

		try {
			listaSimulado = this.simuladoDescricaoDAO.pesquisarPorFiltro(filtro);
			for (Simulado simulado2 : listaSimulado) {

			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(Simulado simulado) throws RNException {
		try {
			this.simuladoDescricaoDAO.remove(simulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO
					.getChave()));
		}

	}

	@Override
	public List<Simulado> pesquisarPorFiltro(Filter filtro)
			throws RNException {
		List<Simulado> listaSimulado = null;
		try {
			listaSimulado = this.simuladoDescricaoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaSimulado;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.simuladoDescricaoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Simulado> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<Simulado> listaSimulado = null;
		try {
			listaSimulado = this.simuladoDescricaoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaSimulado;
	}

	@Override
	public List<SimuladoDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException {
		List<SimuladoDTO> listaSimuladoDTO = null;
		try {
			listaSimuladoDTO = this.simuladoDescricaoDAO.pesquisarPorFiltroDTO(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaSimuladoDTO;
	}

}
