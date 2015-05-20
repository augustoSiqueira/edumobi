package br.com.edu_mob.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.controller.RespostaSimuladoController;
import br.com.edu_mob.dao.RespostaSimuladoDAO;
import br.com.edu_mob.dao.ResultadoSimuladoDAO;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Service("respostaSimuladoController")
public class RespostaSimuladoControllerImpl implements RespostaSimuladoController {

	private static final Logger logger = Logger.getLogger(RespostaSimuladoControllerImpl.class.getName());

	@Autowired
	private RespostaSimuladoDAO respostaSimuladoDAO;

	@Autowired
	private ResultadoSimuladoDAO resultadoSimuladoDAO;

	@Override
	public List<RespostaSimulado> listar() throws RNException {
		List<RespostaSimulado> listaRespostaSimulado = null;
		try {
			listaRespostaSimulado = this.respostaSimuladoDAO.findAll(RespostaSimulado.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaSimulado;
	}

	@Override
	public RespostaSimulado pesquisarPorId(Long id) throws RNException {
		RespostaSimulado respostaSimulado = null;
		try {
			respostaSimulado = this.respostaSimuladoDAO.findById(RespostaSimulado.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return respostaSimulado;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void salvar(List<Questao> listaQuestoes, Simulado simulado, ResultadoSimulado resultadoSimulado, Usuario usuario) throws RNException {
		int qtdCorretas = 0;
		List<RespostaSimulado> listaRespostasSimulado = new ArrayList<RespostaSimulado>();
		try {
			if((listaQuestoes != null) && !listaQuestoes.isEmpty()) {
				for (int i = 0; i < simulado.getQntQuestao(); i++) {
					RespostaSimulado respostaSimulado = new RespostaSimulado();
					respostaSimulado.setCategoria(listaQuestoes.get(i).getAreaConhecimento().getCategoria());
					respostaSimulado.setAreaConhecimento(listaQuestoes.get(i).getAreaConhecimento());
					respostaSimulado.setQuestao(listaQuestoes.get(i));
					if((listaQuestoes.get(i).getAlternativa() != null) && (listaQuestoes.get(i).getAlternativa().getId() != null)) {
						respostaSimulado.setAlternativaSelecionada(listaQuestoes.get(i).getAlternativa());
					}
					respostaSimulado.setAlternativaCorreta(retornarAlternativaCorreta(listaQuestoes.get(i).getListaAlternativas()));
					respostaSimulado.setUsuario(usuario);
					if(listaQuestoes.get(i).getCorreta() != null) {
						respostaSimulado.setCorreta(listaQuestoes.get(i).getCorreta());
					} else {
						respostaSimulado.setCorreta(Boolean.FALSE);
					}
					if((listaQuestoes.get(i).getCorreta() != null) && listaQuestoes.get(i).getCorreta()) {
						qtdCorretas++;
					}
					respostaSimulado.setSimulado(simulado);
					respostaSimulado.setResultadoSimulado(resultadoSimulado);
					listaRespostasSimulado.add(respostaSimulado);
					listaQuestoes.remove(listaQuestoes.get(i));
				}
				resultadoSimulado.setQtdAcertos(qtdCorretas);
				resultadoSimulado.setQtdErros(simulado.getQntQuestao() - qtdCorretas);
				this.respostaSimuladoDAO.salvar(listaRespostasSimulado, resultadoSimulado);
			}
		}  catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}  catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	/**
	 * Metodo responsavel por retornar a alternativa correta da Questao
	 * @param List<Alternativa> listaAlternativas
	 * @return Alternativa alternativa
	 * @throws RNException
	 */

	public static Alternativa retornarAlternativaCorreta(List<Alternativa> listaAlternativas) throws RNException {
		if((listaAlternativas != null) && !listaAlternativas.isEmpty()) {
			for (Alternativa alternativa : listaAlternativas) {
				if(alternativa.getCorreta()) {
					return alternativa;
				}
			}
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void incluir(RespostaSimulado respostaSimulado) throws RNException {
		try {
			this.respostaSimuladoDAO.save(respostaSimulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void alterar(RespostaSimulado respostaSimulado) throws RNException {
		try {
			this.respostaSimuladoDAO.update(respostaSimulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void excluir(RespostaSimulado respostaSimulado) throws RNException {
		try {
			this.respostaSimuladoDAO.remove(respostaSimulado);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<RespostaSimulado> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<RespostaSimulado> listaRespostaSimulado = null;
		try {
			listaRespostaSimulado = this.respostaSimuladoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaSimulado;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int count = 0;
		try {
			count = this.respostaSimuladoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<RespostaSimulado> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws RNException {
		List<RespostaSimulado> listaRespostaSimulado = null;
		try {
			listaRespostaSimulado = this.respostaSimuladoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaRespostaSimulado;
	}

}
