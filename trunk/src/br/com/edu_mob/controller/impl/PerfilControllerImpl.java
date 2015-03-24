package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.controller.PerfilController;
import br.com.edu_mob.dao.PerfilDAO;
import br.com.edu_mob.dao.UsuarioDAO;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Service("perfilController")
public class PerfilControllerImpl implements PerfilController {

	private static final Logger logger = Logger.getLogger(PerfilControllerImpl.class.getName());

	@Autowired
	private PerfilDAO perfilDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	public List<Perfil> listar() throws RNException {
		List<Perfil> listaPerfis = null;
		try {
			listaPerfis = this.perfilDAO.findAll(Perfil.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaPerfis;
	}

	@Override
	public Perfil pesquisarPorId(Long id) throws RNException {
		Perfil perfil = null;
		try {
			perfil = this.perfilDAO.findById(Perfil.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return perfil;
	}

	@Override
	public void validarNome(Perfil perfil) throws RNException {
		try {
			if("".equals(perfil.getNome().trim())){
				throw new RNException(ErrorMessage.USUARIO_NOME_VAZIO.getChave());				
			}
			if (this.perfilDAO.verificarExistencia("nome", perfil.getNome(), perfil.getId())) {
				throw new RNException(ErrorMessage.PERFIL_NOME_EXISTENTE.getChave());
			}
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void validarFuncionalidade(Perfil perfil) throws RNException {
		if ((perfil.getListaFuncionalidades() == null) || perfil.getListaFuncionalidades().isEmpty()) {
			throw new RNException(ErrorMessage.PERFIL_FUNCIONALIDADE_OBRIGATORIA.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void incluir(Perfil perfil) throws RNException {
		try {
			this.validarNome(perfil);
			this.validarFuncionalidade(perfil);
			this.perfilDAO.save(perfil);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void alterar(Perfil perfil) throws RNException {
		try {
			this.validarNome(perfil);
			this.validarFuncionalidade(perfil);
			this.perfilDAO.update(perfil);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void excluir(Perfil perfil) throws RNException {
		Filter filtro = new Filter();
		try {
			filtro.put("idPerfil", perfil.getId().toString());
			if(this.usuarioDAO.pesquisarPorFiltroCount(filtro) > 0) {
				throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DEPENDENCIA_EXISTENTE.getChave(), Entidades.PERFIL.getValor()));
			}
			this.perfilDAO.remove(perfil);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<Perfil> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Perfil> listaPerfis = null;
		try {
			listaPerfis = this.perfilDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaPerfis;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.perfilDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<Perfil> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws RNException {
		List<Perfil> listaPerfis = null;
		try {
			listaPerfis = this.perfilDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaPerfis;
	}

}
