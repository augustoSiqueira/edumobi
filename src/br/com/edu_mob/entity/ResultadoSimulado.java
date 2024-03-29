package br.com.edu_mob.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="resultado_simulado")
@SequenceGenerator(name="resultado_simulado_seq", sequenceName="resultado_simulado_seq")
public class ResultadoSimulado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="resultado_simulado_seq")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_hora_inicio")
	private Date dataHoraInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_hora_fim")
	private Date dataHoraFim;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="tempo_total")
	private Date tempoTotal;

	@Column(name="qtd_acertos")
	private Integer qtdAcertos;

	@Column(name="qtd_erros")
	private Integer qtdErros;

	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="id_simulado")
	private Simulado simulado;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="resultadoSimulado")
	@Fetch(FetchMode.SUBSELECT)
	private List<RespostaSimulado> listaRespostaSimulado;

	public ResultadoSimulado() {
		super();
	}

	public ResultadoSimulado(Date dataHoraInicio, Date dataHoraFim, Integer qtdAcertos,
			Integer qtdErros, Usuario usuario, Simulado simulado) {
		super();
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.qtdAcertos = qtdAcertos;
		this.qtdErros = qtdErros;
		this.usuario = usuario;
		this.simulado = simulado;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraInicio() {
		return this.dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return this.dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Date getTempoTotal() {
		return this.tempoTotal;
	}

	public void setTempoTotal(Date tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Simulado getSimulado() {
		return this.simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
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

	public List<RespostaSimulado> getListaRespostaSimulado() {
		return this.listaRespostaSimulado;
	}

	public void setListaRespostaSimulado(List<RespostaSimulado> listaRespostaSimulado) {
		this.listaRespostaSimulado = listaRespostaSimulado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		ResultadoSimulado other = (ResultadoSimulado) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}


}
