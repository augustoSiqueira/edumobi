package br.com.edu_mob.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String enunciado;

	private String observacao;

	private String caminhoImagem;

	private Long idAreaConhecimento;

	private Date dataAtualizacao;

	private List<AlternativaDTO> listaAlternativasDTO;

	public QuestaoDTO() {
		super();
	}

	public QuestaoDTO(Long id, String enunciado, String observacao, String caminhoImagem, Long idAreaConhecimento,
			Date dataAtualizacao) {
		super();
		this.id = id;
		this.enunciado = enunciado;
		this.observacao = observacao;
		this.caminhoImagem = caminhoImagem;
		this.idAreaConhecimento = idAreaConhecimento;
		this.dataAtualizacao = dataAtualizacao;
		this.listaAlternativasDTO = new ArrayList<AlternativaDTO>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnunciado() {
		return this.enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCaminhoImagem() {
		return this.caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public Long getIdAreaConhecimento() {
		return this.idAreaConhecimento;
	}

	public void setIdAreaConhecimento(Long idAreaConhecimento) {
		this.idAreaConhecimento = idAreaConhecimento;
	}

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public List<AlternativaDTO> getListaAlternativasDTO() {
		return listaAlternativasDTO;
	}

	public void setListaAlternativasDTO(List<AlternativaDTO> listaAlternativasDTO) {
		this.listaAlternativasDTO = listaAlternativasDTO;
	}

}
