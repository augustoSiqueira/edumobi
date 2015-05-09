package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.services.SimuladoDTO;
import br.com.edu_mob.util.Filter;

public interface SimuladoDescricaoController extends GenericController<Simulado>{

	/**
	 * Metodo responsavel por retornar Simulados para web service
	 * @param Filter filtro
	 * @return List<SimuladoDTO> listaSimuladoDTO
	 * @throws RNException
	 */
	List<SimuladoDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException;

}
