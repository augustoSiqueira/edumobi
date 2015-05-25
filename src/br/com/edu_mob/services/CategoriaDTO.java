package br.com.edu_mob.services;

import java.io.Serializable;
import java.util.Date;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private boolean ativo;

	private Long idPai;

	private String titulo;

	private String descricao;

	private boolean curso;

	private Date dataAtualizacao;

	public CategoriaDTO() {
		super();
	}

	public CategoriaDTO(Long id, String nome, boolean ativo, Long idPai, String titulo,
			String descricao, boolean curso, Date dataAtualizacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.ativo = ativo;
		this.idPai = idPai;
		this.titulo = titulo;
		this.descricao = descricao;
		this.curso = curso;
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Long getIdPai() {
		return this.idPai;
	}

	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isCurso() {
		return this.curso;
	}

	public void setCurso(boolean curso) {
		this.curso = curso;
	}

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}



}
