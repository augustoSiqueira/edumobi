package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.exception.RNException;

public interface AreaConhecimentoController extends GenericController<AreaConhecimento>{

	void incluirLista(List<AreaConhecimento> listaAreasConhecimento)
			throws RNException;

}
