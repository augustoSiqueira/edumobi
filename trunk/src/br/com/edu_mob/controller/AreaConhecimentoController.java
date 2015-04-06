package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.services.AreaConhecimentoDTO;
import br.com.edu_mob.util.Filter;

public interface AreaConhecimentoController extends GenericController<AreaConhecimento>{

	public void incluirLista(List<AreaConhecimento> listaAreasConhecimento)
			throws RNException;
	public List<AreaConhecimento> incluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);
	public AreaConhecimento alterarEmMemoria(AreaConhecimento areaConhecimento);
	public List<AreaConhecimento> excluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);
	public void validarListaMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista) throws RNException;

	/**
	 * Metodo responsavel por retornar DTOs para transmissao mobile
	 *
	 * @param Filter filtro
	 * @return List<AreaConhecimentoDTO> listaAreaConhecimentoDTO
	 * @throws RNException
	 */
	List<AreaConhecimentoDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException;
}
