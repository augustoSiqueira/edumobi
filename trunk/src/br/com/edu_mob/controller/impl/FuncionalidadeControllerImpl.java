package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.FuncionalidadeController;
import br.com.edu_mob.dao.FuncionalidadeDAO;
import br.com.edu_mob.entity.Funcionalidade;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;


@Service("funcionalidadeController")
public class FuncionalidadeControllerImpl implements FuncionalidadeController {

	private static final Logger logger = Logger.getLogger(FuncionalidadeControllerImpl.class.getName());

	@Autowired
	private FuncionalidadeDAO funcionalidadeDAO;

	@Override
	public List<Funcionalidade> listar() throws RNException {
		List<Funcionalidade> listaFuncionalidades = null;
		try {
			listaFuncionalidades = this.funcionalidadeDAO.findAll(Funcionalidade.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaFuncionalidades;
	}

	@Override
	public Funcionalidade pesquisarPorId(Long id) throws RNException {
		Funcionalidade funcionalidade = null;
		try {
			funcionalidade = this.funcionalidadeDAO.findById(Funcionalidade.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return funcionalidade;
	}

	@Override
	public void incluir(Funcionalidade t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(Funcionalidade t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Funcionalidade t) throws RNException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Funcionalidade> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Funcionalidade> listaFuncionalidades = null;
		try {
			listaFuncionalidades = this.funcionalidadeDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaFuncionalidades;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Funcionalidade> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize)
			throws RNException {
		// TODO Auto-generated method stub
		return null;
	}

}
