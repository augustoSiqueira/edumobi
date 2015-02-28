package br.com.edu_mob.controller;

import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;

public interface UsuarioController extends GenericController<Usuario> {

	/**
	 * Metodo responsavel por verificar Email do Usuario
	 * 
	 * @param Usuario usuario
	 * @throws RNException
	 */

	void validarEmail(Usuario usuario) throws RNException;

	/**
	 * Metodo responsavel por verificar Email do Usuario
	 * 
	 * @param Usuario usuario
	 * @throws RNException
	 */

	void verificarExistenciaEmail(Usuario usuario) throws RNException;

	/**
	 * Metodo responsavel por verificar se CPF existe
	 * 
	 * @param Usuario usuario
	 * @throws RNException
	 */

	void validarCPF(Usuario usuario) throws RNException;

	/**
	 * Metodo responsavel por verificar se CPF existe
	 * 
	 * @param Usuario usuario
	 * @throws RNException
	 */

	void verificarExistenciaCPF(Usuario usuario) throws RNException;

}
