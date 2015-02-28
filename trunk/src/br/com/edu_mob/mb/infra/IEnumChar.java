package br.com.edu_mob.mb.infra;

import java.io.Serializable;

public interface IEnumChar extends Serializable {

	/**
	 * Retorna o c�digo do �tem da enumera��o.
	 *
	 * @return codigo c�digo do �tem da enumera��o
	 */
	String getCodigo();

	/**
	 * Retorna a descri��o do �tem da enumera��o.
	 *
	 * @return descricao descri��o do �tem da enumera��o
	 */
	String getDescricao();
}
