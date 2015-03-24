package br.com.edu_mob.controller;

import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.exception.RNException;

public interface AlunoController extends GenericController<Aluno> {

	void incluirPreviamente(Aluno aluno) throws RNException;

}
