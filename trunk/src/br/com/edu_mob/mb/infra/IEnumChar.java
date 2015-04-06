package br.com.edu_mob.mb.infra;

import java.io.Serializable;

public interface IEnumChar extends Serializable {

	/**
	 * Retorna o código do ítem da enumeração.
	 *
	 * @return codigo código do ítem da enumeração
	 */
	String getCodigo();

	/**
	 * Retorna a descrição do ítem da enumeração.
	 *
	 * @return descricao descrição do ítem da enumeração
	 */
	String getDescricao();
}
