package br.com.edu_mob.entity.infra;

import java.io.Serializable;

public class ResultadoSimuladoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeAluno;

	private Integer qtdAcertos;

	private Integer qtdErros;

	private Double percentual;

	public ResultadoSimuladoDTO() {
		super();
	}

	public ResultadoSimuladoDTO(String nomeAluno, Integer qtdAcertos, Integer qtdErros) {
		super();
		this.nomeAluno = nomeAluno;
		this.qtdAcertos = qtdAcertos;
		this.qtdErros = qtdErros;
		this.percentual = new Double((qtdAcertos * new Double((qtdAcertos + qtdErros))) / 100d);
	}

	public String getNomeAluno() {
		return this.nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public Integer getQtdAcertos() {
		return this.qtdAcertos;
	}

	public void setQtdAcertos(Integer qtdAcertos) {
		this.qtdAcertos = qtdAcertos;
	}

	public Integer getQtdErros() {
		return this.qtdErros;
	}

	public void setQtdErros(Integer qtdErros) {
		this.qtdErros = qtdErros;
	}

	public Double getPercentual() {
		return this.percentual;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}

}
