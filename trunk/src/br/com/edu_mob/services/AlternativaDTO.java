package br.com.edu_mob.services;

import java.io.Serializable;

import br.com.edu_mob.entity.enuns.Letra;

public class AlternativaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String resposta;

	private boolean correta;

	private Letra letra;
	

	public AlternativaDTO() {
		super();
	}

	public AlternativaDTO(Long id, String resposta, boolean correta, Letra letra) {
		super();
		this.id = id;
		this.resposta = resposta;
		this.correta = correta;
		this.letra = letra;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResposta() {
		return this.resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public boolean isCorreta() {
		return this.correta;
	}

	public void setCorreta(boolean correta) {
		this.correta = correta;
	}

	public Letra getLetra() {
		return letra;
	}

	public void setLetra(Letra letra) {
		this.letra = letra;
	}

	

}
