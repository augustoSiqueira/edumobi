package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.exception.RNException;



public interface AlternativaController extends  GenericController<Alternativa> {

	public List<Alternativa> incluirEmMemoria(Alternativa alternativa, List<Alternativa> lista);
	public Alternativa alterarEmMemoria(Alternativa alternativa);
	public List<Alternativa> excluirEmMemoria(Alternativa alternativa, List<Alternativa> lista);
	public void validarAlternativas(List<Alternativa> listaAlternativas) throws RNException;
	public void validarAlternativasMemoria(Alternativa alternativa, List<Alternativa> listaAlternativas) throws RNException;
	
}
