package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.exception.RNException;

public interface AreaConhecimentoController extends GenericController<AreaConhecimento>{

	public void incluirLista(List<AreaConhecimento> listaAreasConhecimento)
			throws RNException;
	public List<AreaConhecimento> incluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);
	public AreaConhecimento alterarEmMemoria(AreaConhecimento areaConhecimento);
	public List<AreaConhecimento> excluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);
	public void validarListaMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista) throws RNException;
}
