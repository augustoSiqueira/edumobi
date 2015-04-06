package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.services.CategoriaDTO;
import br.com.edu_mob.util.Filter;

public interface CategoriaDAO extends GenericDAO, PesquisaDAO<Categoria> {

	/**
	 * Metodo responsavel por verificar dependencias da entidade
	 * @param Filter filtro
	 * @return int qtd
	 * @throws DAOException
	 */

	int pesquisarDependencia(Filter filtro) throws DAOException;

	/**
	 * Metodo responsavel por retornar DTOs para transmissao mobile
	 * @param Filter filtro
	 * @return List<CategoriaDTO> listaCategorias
	 * @throws DAOException
	 */

	List<CategoriaDTO> pesquisarPorFiltroDTO(Filter filtro) throws DAOException;

}
