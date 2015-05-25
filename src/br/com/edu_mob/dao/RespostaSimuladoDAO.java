package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.exception.DAOException;

public interface RespostaSimuladoDAO extends GenericDAO, PesquisaDAO<RespostaSimulado> {

	/**
	 *	Metodo responsavel por salvar RespostaSimulado e Resultado Simulado em uma unica transacao
	 *
	 * @param List<RespostaSimulado> listaRespostaSimulado
	 * @param ResultadoSimulado resultadoSimulado
	 * @throws DAOException
	 */

	void salvar(List<RespostaSimulado> listaRespostaSimulado, ResultadoSimulado resultadoSimulado) throws DAOException;

}
