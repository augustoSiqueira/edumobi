package br.com.edu_mob.controller;

import br.com.edu_mob.entity.RespostaEstudo;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

public interface RespostaEstudoController extends GenericController<RespostaEstudo> {

	void salvar(RespostaEstudo respostaEstudo) throws RNException;

	int pesquisarPorFiltroCountHQLRelatorio(Filter filtro) throws RNException;

}
