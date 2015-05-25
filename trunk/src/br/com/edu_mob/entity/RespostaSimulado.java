package br.com.edu_mob.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="resposta_simulado")
@SequenceGenerator(name="resposta_simulado_seq", sequenceName="resposta_simulado_seq")
public class RespostaSimulado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="resposta_simulado_seq")
	private Long id;

	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name="id_area_conhecimento")
	private AreaConhecimento areaConhecimento;

	@ManyToOne
	@JoinColumn(name="id_questao")
	private Questao questao;

	@ManyToOne
	@JoinColumn(name="id_alternativa_correta")
	private Alternativa alternativaCorreta;

	@ManyToOne
	@JoinColumn(name="id_alternativa_selecionada")
	private Alternativa alternativaSelecionada;

	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="id_simulado")
	private Simulado simulado;

	@ManyToOne
	@JoinColumn(name="id_resultado_simulado")
	private ResultadoSimulado resultadoSimulado;

	@Column(nullable=false)
	private boolean correta;

	public RespostaSimulado() {
		super();
	}

	public RespostaSimulado(Categoria categoria, AreaConhecimento areaConhecimento, Questao questao, Usuario usuario,
			boolean correta) {
		super();
		this.categoria = categoria;
		this.areaConhecimento = areaConhecimento;
		this.questao = questao;
		this.usuario = usuario;
		this.correta = correta;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public AreaConhecimento getAreaConhecimento() {
		return this.areaConhecimento;
	}

	public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
		this.areaConhecimento = areaConhecimento;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isCorreta() {
		return this.correta;
	}

	public void setCorreta(boolean correta) {
		this.correta = correta;
	}

	public Alternativa getAlternativaCorreta() {
		return this.alternativaCorreta;
	}

	public void setAlternativaCorreta(Alternativa alternativaCorreta) {
		this.alternativaCorreta = alternativaCorreta;
	}

	public Alternativa getAlternativaSelecionada() {
		return this.alternativaSelecionada;
	}

	public void setAlternativaSelecionada(Alternativa alternativaSelecionada) {
		this.alternativaSelecionada = alternativaSelecionada;
	}

	public Simulado getSimulado() {
		return this.simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}

	public ResultadoSimulado getResultadoSimulado() {
		return this.resultadoSimulado;
	}

	public void setResultadoSimulado(ResultadoSimulado resultadoSimulado) {
		this.resultadoSimulado = resultadoSimulado;
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
		RespostaSimulado other = (RespostaSimulado) obj;
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
