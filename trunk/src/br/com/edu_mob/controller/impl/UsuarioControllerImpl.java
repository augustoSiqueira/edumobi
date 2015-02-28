package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.dao.UsuarioDAO;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;
import br.com.edu_mob.util.Util;

@Service("usuarioController")
public class UsuarioControllerImpl implements UsuarioController {

	private static final Logger logger = Logger.getLogger(UsuarioControllerImpl.class.getName());

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	public List<Usuario> listar() throws RNException {
		List<Usuario> listaUsuarios = null;
		try {
			listaUsuarios = this.usuarioDAO.findAll(Usuario.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUsuarios;
	}

	@Override
	public Usuario pesquisarPorId(Long id) throws RNException {
		Usuario usuario = null;
		try {
			usuario = this.usuarioDAO.findById(Usuario.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return usuario;
	}

	@Override
	public void validarEmail(Usuario usuario) throws RNException {
		if (!Util.validarEmail(usuario.getEmail())) {
			throw new RNException(ErrorMessage.USUARIO_EMAIL_INVALIDO.getChave());
		}
	}

	@Override
	public void verificarExistenciaEmail(Usuario usuario) throws RNException {
		try {
			if (this.usuarioDAO.verificarExistencia("email", usuario.getEmail(), usuario.getId())) {
				throw new RNException(ErrorMessage.USUARIO_EMAIL_EXISTENTE.getChave());
			}
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void validarCPF(Usuario usuario) throws RNException {
		if (!Util.validarCpf(usuario.getCpf())) {
			throw new RNException(ErrorMessage.USUARIO_CPF_INVALIDO.getChave());
		}
	}

	@Override
	public void verificarExistenciaCPF(Usuario usuario) throws RNException {
		try {
			if (this.usuarioDAO.verificarExistencia("cpf", usuario.getCpf(), usuario.getId())) {
				throw new RNException(ErrorMessage.USUARIO_CPF_EXISTENTE.getChave());
			}
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void incluir(Usuario usuario) throws RNException {
		try {
			this.validarCPF(usuario);
			this.validarEmail(usuario);
			this.verificarExistenciaCPF(usuario);
			this.verificarExistenciaEmail(usuario);
			usuario.setCpf(Util.removerCaracteresEspeciais(usuario.getCpf()));
			usuario.setSenha(Util.criptografar(usuario.getSenha()));
			this.usuarioDAO.save(usuario);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void alterar(Usuario usuario) throws RNException {
		try {
			this.validarCPF(usuario);
			this.validarEmail(usuario);
			this.verificarExistenciaCPF(usuario);
			this.verificarExistenciaEmail(usuario);
			usuario.setCpf(Util.removerCaracteresEspeciais(usuario.getCpf()));
			this.usuarioDAO.update(usuario);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void excluir(Usuario usuario) throws RNException {
		try {
			this.usuarioDAO.remove(usuario);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
	}

	@Override
	public List<Usuario> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Usuario> listaUsuarios = null;
		try {
			listaUsuarios = this.usuarioDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUsuarios;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.usuarioDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return count;
	}

	@Override
	public List<Usuario> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws RNException {
		List<Usuario> listaUsuarios = null;
		try {
			listaUsuarios = this.usuarioDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUsuarios;
	}

}
