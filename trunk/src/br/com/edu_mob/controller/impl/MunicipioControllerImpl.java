package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.MunicipioController;
import br.com.edu_mob.dao.MunicipioDAO;
import br.com.edu_mob.entity.Municipio;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Service("municipioController")
public class MunicipioControllerImpl implements MunicipioController {

	private static final Logger logger = Logger.getLogger(MunicipioControllerImpl.class.getName());

	@Autowired
	private MunicipioDAO municipioDAO;

	@Override
	public List<Municipio> listar() throws RNException {
		List<Municipio> listaMunicipios = null;
		try {
			listaMunicipios = this.municipioDAO.findAll(Municipio.class);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaMunicipios;
	}

	@Override
	public Municipio pesquisarPorId(Long id) throws RNException {
		Municipio municipio = null;
		try {
			municipio = this.municipioDAO.findById(Municipio.class, id);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return municipio;
	}

	@Override
	public void incluir(Municipio t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(Municipio t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Municipio t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Municipio> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Municipio> listaMunicipios = null;
		try {
			listaMunicipios = this.municipioDAO.pesquisarPorFiltro(filtro);
		} catch(DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaMunicipios;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.municipioDAO.pesquisarPorFiltroCount(filtro);
		} catch(DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return count;
	}

	@Override
	public List<Municipio> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<Municipio> listaMunicipios = null;
		try {
			listaMunicipios = this.municipioDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch(DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaMunicipios;
	}

}
