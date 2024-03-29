package br.com.edu_mob.controller.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.dao.AreaConhecimentoDAO;
import br.com.edu_mob.dao.CategoriaDAO;
import br.com.edu_mob.dao.impl.CategoriaDAOImpl;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.model.AreaConhecimentoModel;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.CategoriaDTO;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Service("categoriaController")
public class CategoriaControllerImpl implements CategoriaController, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CategoriaDAOImpl.class.getName());

	@Autowired
	private CategoriaDAO categoriaDAO;

	@Autowired
	private AreaConhecimentoDAO  areaConhecimentoDAO;

	@Override
	public List<Categoria> listar() throws RNException {
		List<Categoria> listaCategorias = null;
		try {
			listaCategorias = this.categoriaDAO.findAll(Categoria.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaCategorias;
	}

	@Override
	public Categoria pesquisarPorId(Long id) throws RNException {
		Categoria categoria = null;
		try {
			categoria = this.categoriaDAO.findById(Categoria.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return categoria;
	}

	@Override
	public void validarNome(Categoria categoria) throws RNException {
		try {
			if (this.categoriaDAO.verificarExistencia("nome", categoria.getNome(), categoria.getId())) {
				throw new RNException(ErrorMessage.CATEGORIA_NOME_EXISTENTE.getChave());
			}
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void incluir(Categoria categoria) throws RNException {
		try {
			this.validarCamposVazios(categoria);
			this.validarNome(categoria);
			categoria.setDataAtualizacao(new Date());
			this.categoriaDAO.save(categoria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void alterar(Categoria categoria) throws RNException {
		try {
			this.validarCamposVazios(categoria);
			this.validarNome(categoria);
			categoria.setDataAtualizacao(new Date());
			this.categoriaDAO.update(categoria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void excluir(Categoria categoria) throws RNException {
		Filter filtro = new Filter();
		Filter filtroAreaConhecimento = new Filter();
		try {
			filtro.put("id", categoria.getId().toString());
			if(this.categoriaDAO.pesquisarDependencia(filtro) > 0) {
				throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DEPENDENCIA_EXISTENTE.getChave(), Entidades.CATEGORIA.getValor()));
			}
			filtroAreaConhecimento.put("idCategoria", categoria.getId().toString());
			categoria.setListaAreasConhecimento(this.areaConhecimentoDAO.pesquisarPorFiltro(filtroAreaConhecimento));
			if(categoria.getListaAreasConhecimento().size() > 0) {
				throw new RNException(MensagemUtil.getMensagem(ErrorMessage.DEPENDENCIA_EXISTENTE.getChave(), Entidades.CATEGORIA.getValor()));
			}
			this.categoriaDAO.remove(categoria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<Categoria> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Categoria> listaCategorias = null;
		try {
			listaCategorias = this.categoriaDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaCategorias;
	}

	@Override
	public List<CategoriaDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException {
		List<CategoriaDTO> listaCategoriasDTO = null;
		try {
			listaCategoriasDTO = this.categoriaDAO.pesquisarPorFiltroDTO(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaCategoriasDTO;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.categoriaDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Categoria> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<Categoria> listaCategorias = null;
		try {
			listaCategorias = this.categoriaDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaCategorias;
	}

	public void validarCamposVazios(Categoria categoria) throws RNException{
		List<String> erros = new ArrayList<String>();
		boolean erro = false;

		if( categoria.getNome().trim().equals("")){
			erros.add(ErrorMessage.CAMPO_NOME_VAZIO.getChave());
			erro = true;
		}

		if(categoria.isCurso()){
			if( categoria.getTitulo().trim().equals("")){
				erros.add(ErrorMessage.CAMPO_TITULO_VAZIO.getChave());
				erro = true;
			}
			if( categoria.getDescricao().trim().equals("")){
				erros.add(ErrorMessage.CAMPO_DESCRICAO_VAZIO.getChave());
				erro = true;
			}
		}

		if(erro){
			throw new RNException(erros);
		}


	}
	
	public List<AreaConhecimentoModel> pesquisarAreaConhecimentoModels(Filter filtro) throws RNException{
		List<AreaConhecimentoModel> listaAreaConhecimentoModel = null;
		
		try {
			listaAreaConhecimentoModel = areaConhecimentoDAO.pesquisarAreaConhecimentoModels(filtro);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaAreaConhecimentoModel;
	}

}
