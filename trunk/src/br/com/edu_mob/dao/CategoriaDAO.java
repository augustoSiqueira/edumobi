package br.com.edu_mob.dao;

import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.util.Filter;

public interface CategoriaDAO extends GenericDAO, PesquisaDAO<Categoria> {

	/**
	 * Metodo responsavel por verificar dependencias da entidade
	 * @param Filter filtro
	 * @return int qtd
	 * @throws DAOException
	 */

	int pesquisarDependencia(Filter filtro) throws DAOException;

}
