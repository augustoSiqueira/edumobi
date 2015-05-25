package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.infra.ResultadoSimuladoDTO;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

public interface ResultadoSimuladoController extends GenericController<ResultadoSimulado> {

	/**
	 * Metodo responsavel por pesquisar Resultados de Simulados para Relatorio
	 * @param Filter filtro
	 * @return List<ResultadoSimuladoDTO> listaResultadoSimuladoDTO
	 * @throws RNException
	 */

	List<ResultadoSimuladoDTO> pesquisarRelatorioDesempenhoAlunos(Filter filtro) throws RNException;

	/**
	 * Metodo responsavel por pesquisar Resultados de Simulados para Relatorio
	 * @param Filter filtro
	 * @return List<ResultadoSimuladoDTO> listaResultadosSimuladoDTO
	 * @throws RNException
	 */

	List<ResultadoSimuladoDTO> pesquisarRelatorioRankingAlunos(Filter filtro) throws RNException;

}
