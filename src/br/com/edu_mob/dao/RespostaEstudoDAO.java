package br.com.edu_mob.dao;

import br.com.edu_mob.entity.RespostaEstudo;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.util.Filter;

public interface RespostaEstudoDAO extends GenericDAO, PesquisaDAO<RespostaEstudo> {

	/**
	 *
	 * @param filtro
	 * @return
	 * @throws DAOException
	 */

	int pesquisarPorFiltroCountHQLRelatorio(Filter filtro) throws DAOException;

}
