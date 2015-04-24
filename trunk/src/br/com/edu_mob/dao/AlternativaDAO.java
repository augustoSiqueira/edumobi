package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.services.AlternativaDTO;
import br.com.edu_mob.util.Filter;



public interface AlternativaDAO extends GenericDAO, PesquisaDAO<Alternativa> {

	List<Alternativa> incluirEmMemoria(Alternativa alternativa, List<Alternativa> lista);
	Alternativa alterarEmMemoria(Alternativa alternativa);
	List<Alternativa> excluirEmMemoria(Alternativa alternativa, List<Alternativa> lista);
	List<AlternativaDTO> pesquisarPorFiltroDTO(Filter filtro) throws DAOException;
}
