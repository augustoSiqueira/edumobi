package br.com.edu_mob.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="area_conhecimento")
@SequenceGenerator(name="area_conhecimento_seq", sequenceName="area_conhecimento_seq")
public class AreaConhecimento implements Serializable {

	private static final long serialVersionUID = 8200531734337796100L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="area_conhecimento_seq")
	private Long id;

	@NotEmpty
	@Column(length = 150, nullable = false)
	private String descricao;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_atualizacao")
	private Date dataAtualizacao;

	public AreaConhecimento() {
		super();
	}

	public AreaConhecimento(Categoria categoria) {
		super();
		this.categoria = categoria;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@Override
	public String toString() {
		return "AreaConhecimento [id=" + this.id + ", descricao=" + this.descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.descricao == null) ? 0 : this.descricao.hashCode());
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
		AreaConhecimento other = (AreaConhecimento) obj;
		if (this.descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!this.descricao.equals(other.descricao)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


}