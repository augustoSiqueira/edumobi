package br.com.edu_mob.entity.model;

public class AreaConhecimentoModel {

	private Long id;
	private String descricao;
	private int qntQuestoes;
	
	
	
	public AreaConhecimentoModel() {
		super();
	}
	
	public AreaConhecimentoModel(Long id, String descricao, int qntQuestoes) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.qntQuestoes = qntQuestoes;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getQntQuestoes() {
		return qntQuestoes;
	}
	public void setQntQuestoes(int qntQuestoes) {
		this.qntQuestoes = qntQuestoes;
	}
	
	
	
}
