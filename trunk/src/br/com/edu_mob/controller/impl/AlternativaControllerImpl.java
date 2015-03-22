package br.com.edu_mob.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.AlternativaController;
import br.com.edu_mob.dao.AlternativaDAO;
import br.com.edu_mob.dao.impl.AlternativaDAOImpl;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;

@Service("alternativaController")
public class AlternativaControllerImpl implements AlternativaController {
	
	private static final Logger logger = Logger.getLogger(AlternativaDAOImpl.class.getName());
	
	@Autowired
	private AlternativaDAO alternativaDAO;

	@Override
	public List<Alternativa> listar() throws RNException {
		List<Alternativa> listaAlternativa = null;
		try {
			listaAlternativa = this.alternativaDAO.findAll(Alternativa.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaAlternativa;
	}
	
	@Override
	public Alternativa pesquisarPorId(Long id) throws RNException {
		Alternativa alternativa = null;
		try {
			alternativa = this.alternativaDAO.findById(Alternativa.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return alternativa;
	}
	
	@Override
	public void incluir(Alternativa alternativa) throws RNException {
		try {
			this.alternativaDAO.save(alternativa);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}
	
	public List<Alternativa> incluirEmMemoria(Alternativa alternativa, List<Alternativa> lista){
		return this.alternativaDAO.incluirEmMemoria(alternativa, lista);
	}
	
	public Alternativa alterarEmMemoria(Alternativa alternativa){
		return this.alternativaDAO.alterarEmMemoria(alternativa);
	}
	
	public List<Alternativa> excluirEmMemoria(Alternativa alternativa, List<Alternativa> lista){
		return this.alternativaDAO.excluirEmMemoria(alternativa, lista);
	}
	
	@Override
	public void alterar(Alternativa alternativa) throws RNException {
		try {
			this.alternativaDAO.update(alternativa);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}
	
	@Override
	public void excluir(Alternativa alternativa) throws RNException {
		Filter filtro = new Filter();
		try {
			filtro.put("id", alternativa.getId().toString());
			this.alternativaDAO.remove(alternativa);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}
	
	

	@Override
	public List<Alternativa> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Alternativa> listaAlternativa = null;
		try {
			listaAlternativa = this.alternativaDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaAlternativa;
	}
	
	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.alternativaDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Alternativa> pesquisarPorFiltroPaginada(Filter filtro,
		int primeiroReg, int paginaSize) throws RNException {
		List<Alternativa> listaAlternativa = null;
		try {
			listaAlternativa = this.alternativaDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaAlternativa;
	}
	
	@Override
	public void validarAlternativas(List<Alternativa> listaAlternativas) throws RNException{
		int tamanho = listaAlternativas.size();
		int qtdAlternativasCorretas = 0;
		
		if(tamanho < 2){
			throw new RNException(ErrorMessage.ALTERNATIVAS_INVALIDAS.getChave());
		}
		
		for (Alternativa alternativa : listaAlternativas) {
			
			if(alternativa.getCorreta() == true){
				qtdAlternativasCorretas++;
			}
		}
		
		if(qtdAlternativasCorretas > 1){
			throw new RNException(ErrorMessage.ALTERNATIVA_CORRETA_MAIOR.getChave());
		}
		
		if(qtdAlternativasCorretas == 0){
			throw new RNException(ErrorMessage.ALTERNATIVA_CORRETA_MENOR.getChave());
		}
		
		
			
	}

}
