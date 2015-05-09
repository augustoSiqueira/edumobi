package br.com.edu_mob.services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;

public class SimuladoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String titulo;

	private String descricao;

	private Date duracao;

	private int qtdQuestao;

	private Categoria categoria;

	private List<AreaConhecimento> areasConhecimento;

	public SimuladoDTO() {
		super();
	}

	public SimuladoDTO(Long id, String titulo, String descricao, Date duracao, int qtdQuestao, Categoria categoria,
			List<AreaConhecimento> areasConhecimento) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.duracao = duracao;
		this.qtdQuestao = qtdQuestao;
		this.categoria = categoria;
		this.areasConhecimento = areasConhecimento;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDuracao() {
		return this.duracao;
	}

	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}

	public int getQtdQuestao() {
		return this.qtdQuestao;
	}

	public void setQtdQuestao(int qtdQuestao) {
		this.qtdQuestao = qtdQuestao;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<AreaConhecimento> getAreasConhecimento() {
		return this.areasConhecimento;
	}

	public void setAreasConhecimento(List<AreaConhecimento> areasConhecimento) {
		this.areasConhecimento = areasConhecimento;
	}

}
