package br.com.edu_mob.dao;

import java.util.List;

import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.util.Filter;

public interface PesquisaDAO<T> {

	List<T> pesquisarPorFiltro(Filter filtro) throws DAOException;

	int pesquisarPorFiltroCount(Filter filtro) throws DAOException;

	List<T> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws DAOException;

	boolean verificarExistencia(String campo, String valor, Long id) throws DAOException;

}
