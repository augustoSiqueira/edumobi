package br.com.edu_mob.services;

import java.io.Serializable;
import java.util.Date;

public class AreaConhecimentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String descricao;

	private Long idCategoria;

	private Date dataAtualizacao;

	public AreaConhecimentoDTO() {
		super();
	}

	public AreaConhecimentoDTO(Long id, String descricao, Long idCategoria, Date dataAtualizacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.dataAtualizacao = dataAtualizacao;
	}



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

}
