package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.services.SimuladoDTO;
import br.com.edu_mob.util.Filter;

public interface SimuladoDescricaoDAO  extends GenericDAO, PesquisaDAO<Simulado> {

	/**
	 * Metodo responsavel por retornar Simulados para web service
	 * @param Filter filtro
	 * @return List<SimuladoDTO> listaSimuladoDTO
	 * @throws DAOException
	 */
	List<SimuladoDTO> pesquisarPorFiltroDTO(Filter filtro) throws DAOException;

}
