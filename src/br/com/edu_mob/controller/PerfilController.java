package br.com.edu_mob.controller;

import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.RNException;

public interface PerfilController extends GenericController<Perfil> {

	/**
	 * Metodo responsavel por verificar a existencia de um nome ja cadastrado
	 * 
	 * @param Perfil perfil
	 * @throws RNException
	 */

	void validarNome(Perfil perfil) throws RNException;

	/**
	 * Metodo responsavel por validar a existencia de um Funcionalidade
	 * 
	 * @param perfil
	 * @throws RNException
	 */

	void validarFuncionalidade(Perfil perfil) throws RNException;

}
