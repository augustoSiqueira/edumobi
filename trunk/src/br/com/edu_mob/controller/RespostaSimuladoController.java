package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;

public interface RespostaSimuladoController extends GenericController<RespostaSimulado> {

	/**
	 * Metodo responsavel por salvar uma Respostas de um Simulado
	 *
	 * @param List<Questao> listaQuestoes
	 * @param Simulado simulado
	 * @param ResultadoSimulado resultadoSimulado
	 * @param Usuario usuario
	 * @throws RNException
	 */

	void salvar(List<Questao> listaQuestoes, Simulado simulado, ResultadoSimulado resultadoSimulado, Usuario usuario) throws RNException;

}
