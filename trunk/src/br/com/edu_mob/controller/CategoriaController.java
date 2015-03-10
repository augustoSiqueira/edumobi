package br.com.edu_mob.controller;

import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;

public interface CategoriaController extends GenericController<Categoria> {
	
	/**
	 * Metodo responsavel por verificar se nome ja cadastrado
	 * @param Categoria categoria
	 * @throws RNException
	 */

	void validarNome(Categoria categoria) throws RNException;

}
