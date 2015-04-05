package br.com.edu_mob.controller.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.dao.AreaConhecimentoDAO;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.AreaConhecimentoDTO;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Service("areaConhecimentoController")
public class AreaConhecimentoControllerImpl implements AreaConhecimentoController, Serializable{

	private static final long serialVersionUID = -8150844223995268988L;

	private static final Logger logger = Logger.getLogger(AreaConhecimentoControllerImpl.class.getName());
	
	@Autowired
	private AreaConhecimentoDAO areaConhecimentoDAO;
	
	@Override
	public List<AreaConhecimento> listar() throws RNException {
		List<AreaConhecimento> listaAreaConhecimento = null;
		try {
			listaAreaConhecimento = this.areaConhecimentoDAO.findAll(AreaConhecimento.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaAreaConhecimento;
	}

	@Override
	public AreaConhecimento pesquisarPorId(Long id) throws RNException {
		AreaConhecimento areaConhecimento = null;
		try {
			areaConhecimento = this.areaConhecimentoDAO.findById(AreaConhecimento.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return areaConhecimento;
	}
	
	@Override
	public void incluirLista(List<AreaConhecimento> listaAreasConhecimento) throws RNException {
		try {
			if((listaAreasConhecimento != null) && !listaAreasConhecimento.isEmpty()) {
				for (AreaConhecimento areaConhecimento : listaAreasConhecimento) {
					if(areaConhecimento.getId() != null) {
						this.alterar(areaConhecimento);
					} else {
						this.incluir(areaConhecimento);
					}
				}
			}
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void incluir(AreaConhecimento areaConhecimento) throws RNException {
		try {
			if((areaConhecimento.getDescricao() != null) || !areaConhecimento.getDescricao().isEmpty()){
				areaConhecimento.setDataAtualizacao(new Date());
				this.areaConhecimentoDAO.save(areaConhecimento);
			}
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void alterar(AreaConhecimento areaConhecimento) throws RNException {
		try {
			if((areaConhecimento.getDescricao() != null) || !areaConhecimento.getDescricao().isEmpty()){
				areaConhecimento.setDataAtualizacao(new Date());
				this.areaConhecimentoDAO.update(areaConhecimento);
			}
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void excluir(AreaConhecimento areaConhecimento) throws RNException {
		try {
			this.areaConhecimentoDAO.remove(areaConhecimento);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
	}

	@Override
	public List<AreaConhecimento> pesquisarPorFiltro(Filter filtro)
			throws RNException {
		List<AreaConhecimento> listaAreaConhecimento = null;
		try {
			listaAreaConhecimento = this.areaConhecimentoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaAreaConhecimento;
	}

	@Override
	public List<AreaConhecimentoDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException {
		List<AreaConhecimentoDTO> listaAreaConhecimentoDTO = null;
		try {
			listaAreaConhecimentoDTO = this.areaConhecimentoDAO.pesquisarPorFiltroDTO(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaAreaConhecimentoDTO;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.areaConhecimentoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return count;
	}

	@Override
	public List<AreaConhecimento> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<AreaConhecimento> listaAreaConhecimento = null;
		try {
			listaAreaConhecimento = this.areaConhecimentoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaAreaConhecimento;
	}

	@Override
	public List<AreaConhecimento> incluirEmMemoria(
			AreaConhecimento areaConhecimento, List<AreaConhecimento> lista) {
		return this.areaConhecimentoDAO.incluirEmMemoria(areaConhecimento, lista);
	}

	@Override
	public AreaConhecimento alterarEmMemoria(AreaConhecimento areaConhecimento) {
		return this.areaConhecimentoDAO.alterarEmMemoria(areaConhecimento);
	}

	@Override
	public List<AreaConhecimento> excluirEmMemoria(
			AreaConhecimento areaConhecimento, List<AreaConhecimento> lista) {
		return this.areaConhecimentoDAO.excluirEmMemoria(areaConhecimento, lista);
	}

	@Override
	public void validarListaMemoria(AreaConhecimento areaConhecimento,
			List<AreaConhecimento> lista) throws RNException {
		// TODO Auto-generated method stub
		
	}
}