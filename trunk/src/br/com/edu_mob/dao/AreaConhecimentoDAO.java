package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.AreaConhecimento;

public interface AreaConhecimentoDAO extends GenericDAO, PesquisaDAO<AreaConhecimento>{

	public List<AreaConhecimento> incluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);
	public AreaConhecimento alterarEmMemoria(AreaConhecimento areaConhecimento);
	public List<AreaConhecimento> excluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);
	
}
