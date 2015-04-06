package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.entity.Alternativa;



public interface AlternativaDAO extends GenericDAO, PesquisaDAO<Alternativa> {

	public List<Alternativa> incluirEmMemoria(Alternativa alternativa, List<Alternativa> lista);
	public Alternativa alterarEmMemoria(Alternativa alternativa);
	public List<Alternativa> excluirEmMemoria(Alternativa alternativa, List<Alternativa> lista);
}
