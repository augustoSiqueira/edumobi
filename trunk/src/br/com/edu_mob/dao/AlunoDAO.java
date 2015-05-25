package br.com.edu_mob.dao;

import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.exception.DAOException;

public interface AlunoDAO extends GenericDAO, PesquisaDAO<Aluno> {

	/**
	 * Metodo responsavel por retornar ultimo ID para geracao da matricula do Aluno
	 * @return long id
	 * @throws DAOException
	 */

	long retornarUltimoID() throws DAOException;

	/**
	 * Metodo responsavel por validar login do usuario para servico
	 *
	 * @param String email
	 * @param String senha
	 * @return Aluno aluno
	 * @throws DAOException
	 */

	Aluno pesquisarLoginAluno(String email, String senha) throws DAOException;

}
