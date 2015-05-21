package br.com.edu_mob.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table
@SequenceGenerator(name="simulado_seq", sequenceName="simulado_seq")
public class Simulado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="simulado_seq")
	private Long id;

	@Column(length = 100, nullable = false)
	private String titulo;

	@Column(length = 255, nullable = false)
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="duracao")
	private Date duracao;

	@Column(name="qtd_questoes")
	private int qntQuestao;

	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="simulado")
	@Fetch(FetchMode.SUBSELECT)
	private List<AreaConhecimento> areasConhecimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_atualizacao")
	private Date dataAtualizacao;

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

	public int getQntQuestao() {
		return this.qntQuestao;
	}

	public void setQntQuestao(int qntQuestao) {
		this.qntQuestao = qntQuestao;
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

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
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
		Simulado other = (Simulado) obj;
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
