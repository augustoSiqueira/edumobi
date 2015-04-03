package br.com.edu_mob.controller;


import java.util.List;

import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.services.QuestaoDTO;
import br.com.edu_mob.util.Filter;


public interface QuestaoController extends  GenericController<Questao> {

	/**
	 * Metodo responsavel por retornar DTOs para transmissao mobile
	 *
	 * @param Filter filtro
	 * @return List<QuestaoDTO> listaQuestoes
	 * @throws DAOException
	 */
	List<QuestaoDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException;

}
