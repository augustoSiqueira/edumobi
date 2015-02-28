package br.com.edu_mob.controller;

import java.util.List;

import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

public interface GenericController<T> {

	List<T> listar() throws RNException;

	T pesquisarPorId(Long id) throws RNException;

	void incluir(T t) throws RNException;

	void alterar(T t) throws RNException;

	void excluir(T t) throws RNException;

	List<T> pesquisarPorFiltro(Filter filtro) throws RNException;

	int pesquisarPorFiltroCount(Filter filtro) throws RNException;

	List<T> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws RNException;


}
