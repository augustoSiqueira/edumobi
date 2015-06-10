package br.com.edu_mob.entity.infra;

import java.io.Serializable;
import java.util.Date;

public class ResultadoSimuladoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String nomeAluno;

	private Integer qtdAcertos;

	private Integer qtdErros;
	
	private Date dataHoraInicio;
	
	private Date tempoTotal;

	private Double percentual;

	public ResultadoSimuladoDTO() {
		super();
	}

	public ResultadoSimuladoDTO(Long id, Integer qtdAcertos, Integer qtdErros,
			Date dataHoraInicio, Date tempoTotal) {
		super();
		this.id = id;
		this.qtdAcertos = qtdAcertos;
		this.qtdErros = qtdErros;
		this.dataHoraInicio = dataHoraInicio;
		this.tempoTotal = tempoTotal;
	}

	public ResultadoSimuladoDTO(String nomeAluno, Integer qtdAcertos, Integer qtdErros) {
		super();
		this.nomeAluno = nomeAluno;
		this.qtdAcertos = qtdAcertos;
		this.qtdErros = qtdErros;
		this.percentual = new Double((qtdAcertos * new Double((qtdAcertos + qtdErros))) / 100d);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(Date tempoTotal) {
		this.tempoTotal = tempoTotal;
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
