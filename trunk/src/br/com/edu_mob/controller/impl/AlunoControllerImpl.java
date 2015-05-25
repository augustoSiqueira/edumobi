package br.com.edu_mob.controller.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.dao.AlunoDAO;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.EmailUtil;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.Util;

@Service("alunoController")
public class AlunoControllerImpl implements AlunoController, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AlunoControllerImpl.class.getName());

	@Autowired
	private AlunoDAO alunoDAO;

	@Autowired
	private UsuarioController usuarioController;

	private static final long ID_PERFIL_ALUNO = 2L;

	@Override
	public List<Aluno> listar() throws RNException {
		List<Aluno> listaAlunos = null;
		try {
			listaAlunos = this.alunoDAO.findAll(Aluno.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaAlunos;
	}

	@Override
	public Aluno pesquisarPorId(Long id) throws RNException {
		Aluno aluno = null;
		try {
			aluno = this.alunoDAO.findById(Aluno.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return aluno;
	}

	@Override
	public Aluno validarAcessoServico(String email, String senha) throws RNException {
		Aluno aluno = null;
		try {
			aluno = this.alunoDAO.pesquisarLoginAluno(email, senha);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return aluno;
	}

	public void validarDataNascimento(Aluno aluno) throws RNException {
		if(aluno.getDataNascimento() != null) {
			Calendar dataNascimento = Calendar.getInstance();
			Calendar dataAtual = Calendar.getInstance();
			dataNascimento.setTime(aluno.getDataNascimento());
			if(dataNascimento.after(dataAtual)) {
				throw new RNException(ErrorMessage.ALUNO_DATA_NASCIMENTO_MAIOR_DATA_ATUAL.getChave());
			}
		}
	}

	@Override
	public void incluirPreviamente(Aluno aluno) throws RNException {
		try {
			this.usuarioController.validarCPF(aluno);
			this.usuarioController.validarEmail(aluno);
			this.usuarioController.verificarExistenciaCPF(aluno);
			this.usuarioController.verificarExistenciaEmail(aluno);
			aluno.setCpf(Util.removerCaracteresEspeciais(aluno.getCpf()));
			aluno.setMatricula(Util.gerarMatricula(this.alunoDAO.retornarUltimoID()));
			aluno.setSenha(Util.gerarSenha(8));
			EmailUtil.enviarEmail("systemedumobi@gmail.com", aluno.getEmail(), "Senha Eduobi", "Sua senha é: " + aluno.getSenha());
			aluno.setSenha(Util.criptografar(aluno.getSenha()));
			aluno.setPerfil(new Perfil(ID_PERFIL_ALUNO));
			this.alunoDAO.save(aluno);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}  catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void incluir(Aluno aluno) throws RNException {
		try {
			this.usuarioController.validarCPF(aluno);
			this.usuarioController.validarEmail(aluno);
			this.usuarioController.verificarExistenciaCPF(aluno);
			this.usuarioController.verificarExistenciaEmail(aluno);
			this.validarDataNascimento(aluno);
			if((aluno.getTelefoneResidencial() != null) && !aluno.getTelefoneResidencial().isEmpty() ) {
				aluno.setTelefoneResidencial(Util.removerCaracteresEspeciais(aluno.getTelefoneResidencial()));
			}
			if((aluno.getCelular() != null) && !aluno.getCelular().isEmpty()) {
				aluno.setCelular(Util.removerCaracteresEspeciais(aluno.getCelular()));
			}
			if((aluno.getCep() != null) && !aluno.getCep().isEmpty()) {
				aluno.setCep(Util.removerCaracteresEspeciais(aluno.getCep()));
			}
			aluno.setCpf(Util.removerCaracteresEspeciais(aluno.getCpf()));
			aluno.setMatricula(Util.gerarMatricula(this.alunoDAO.retornarUltimoID()));
			aluno.setSenha(Util.gerarSenha(8));
			EmailUtil.enviarEmail("systemedumobi@gmail.com", aluno.getEmail(), "Senha Eduobi", "Sua senha é: " + aluno.getSenha());
			aluno.setSenha(Util.criptografar(aluno.getSenha()));
			aluno.setPerfil(new Perfil(ID_PERFIL_ALUNO));
			this.alunoDAO.save(aluno);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}  catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void alterar(Aluno aluno) throws RNException {
		try {
			this.usuarioController.validarCPF(aluno);
			this.usuarioController.validarEmail(aluno);
			this.usuarioController.verificarExistenciaCPF(aluno);
			this.usuarioController.verificarExistenciaEmail(aluno);
			this.validarDataNascimento(aluno);
			if((aluno.getTelefoneResidencial() != null) && !aluno.getTelefoneResidencial().isEmpty() ) {
				aluno.setTelefoneResidencial(Util.removerCaracteresEspeciais(aluno.getTelefoneResidencial()));
			}
			if((aluno.getCelular() != null) && !aluno.getCelular().isEmpty()) {
				aluno.setCelular(Util.removerCaracteresEspeciais(aluno.getCelular()));
			}
			if((aluno.getCep() != null) && !aluno.getCep().isEmpty()) {
				aluno.setCep(Util.removerCaracteresEspeciais(aluno.getCep()));
			}
			aluno.setCpf(Util.removerCaracteresEspeciais(aluno.getCpf()));
			this.alunoDAO.update(aluno);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void excluir(Aluno aluno) throws RNException {
		try {
			this.alunoDAO.remove(aluno);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<Aluno> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Aluno> listaAlunos = null;
		try {
			listaAlunos = this.alunoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaAlunos;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.alunoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Aluno> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<Aluno> listaAlunos = null;
		try {
			listaAlunos = this.alunoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaAlunos;
	}

}
