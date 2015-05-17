package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.model.AreaConhecimentoModel;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.services.CategoriaDTO;
import br.com.edu_mob.util.Filter;

public interface CategoriaController extends GenericController<Categoria> {

	/**
	 * Metodo responsavel por verificar se nome ja cadastrado
	 * @param Categoria categoria
	 * @throws RNException
	 */

	void validarNome(Categoria categoria) throws RNException;

	/**
	 *Metodo responsavel por retornar DTOs para transmissao mobile
	 *
	 * @param Filter filtro
	 * @return List<CategoriaDTO> listaCategorias
	 * @throws RNException
	 */

	List<CategoriaDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException;

	List<AreaConhecimentoModel> pesquisarAreaConhecimentoModels(Filter filtro) throws RNException;
	
}
