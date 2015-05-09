package br.com.edu_mob.controller;


import java.util.List;

import org.primefaces.event.FileUploadEvent;

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

	String salvarImagem(FileUploadEvent event) throws RNException;

	/**
	 * Metodo responsavel por pesquisar a quantidade total de Questoes por filtro
	 *
	 * @param Filter filtro
	 * @return int qtdTotal
	 * @throws DAOException
	 */

	int pesquisarQtdTotalQuestoes(Filter filtro) throws RNException;

	/**
	 * Metodo responsavel por pesquisar Questoes para o Simulado
	 * @param Filter filtro
	 * @return List<Questao> listaQuestoes
	 * @throws DAOException
	 */

	List<Questao> pesquisarSimulado(Filter filtro) throws RNException;

	/**
	 * Metodo responsavel por pesquisar Questoes para o Simulado
	 * @param Filter filtro
	 * @return List<QuestaoDTO> listaQuestoesDTO
	 * @throws RNException
	 */

	List<QuestaoDTO> pesquisarSimuladoDTO(Filter filtro) throws RNException;

}
