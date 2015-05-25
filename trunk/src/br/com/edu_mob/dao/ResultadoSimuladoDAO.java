package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.infra.ResultadoSimuladoDTO;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.util.Filter;

public interface ResultadoSimuladoDAO extends GenericDAO, PesquisaDAO<ResultadoSimulado> {

	/**
	 * Metodo responsavel por pesquisar Resultados de Simulados para Relatorio
	 * @param Filter filtro
	 * @return List<ResultadoSimuladoDTO> listaResultadoSimuladoDTO
	 * @throws DAOException
	 */

	List<ResultadoSimuladoDTO> pesquisarRelatorioDesempenhoAlunos(Filter filtro) throws DAOException;

	/**
	 * Metodo responsavel por pesquisar Resultados de Simulados para Relatorio
	 * @param Filter filtro
	 * @return List<ResultadoSimuladoDTO> listaResultadosSimuladoDTO
	 * @throws DAOException
	 */

	List<ResultadoSimuladoDTO> pesquisarRelatorioRankingAlunos(Filter filtro) throws DAOException;

}
