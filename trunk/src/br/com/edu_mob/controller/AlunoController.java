package br.com.edu_mob.controller;

import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.exception.RNException;

public interface AlunoController extends GenericController<Aluno> {

	/**
	 * Metodo responsavel por incluir um Aluno previamente
	 * @param Aluno aluno
	 * @throws RNException
	 */

	void incluirPreviamente(Aluno aluno) throws RNException;

	/**
	 * Metodo responsavel por validar login do usuario (Servico)
	 *
	 * @param String email
	 * @param String senha
	 * @return Aluno aluno
	 * @throws RNException
	 */

	Aluno validarAcessoServico(String email, String senha) throws RNException;

}
