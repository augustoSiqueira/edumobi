package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.model.AreaConhecimentoModel;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.services.AreaConhecimentoDTO;
import br.com.edu_mob.util.Filter;

public interface AreaConhecimentoDAO extends GenericDAO, PesquisaDAO<AreaConhecimento>{

	List<AreaConhecimento> incluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);

	AreaConhecimento alterarEmMemoria(AreaConhecimento areaConhecimento);

	List<AreaConhecimento> excluirEmMemoria(AreaConhecimento areaConhecimento, List<AreaConhecimento> lista);

	List<AreaConhecimentoDTO> pesquisarPorFiltroDTO(Filter filtro) throws DAOException;

	List<AreaConhecimentoModel> pesquisarAreaConhecimentoModels(Filter filtro) throws DAOException;
}
