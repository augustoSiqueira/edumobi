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
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
@SequenceGenerator(name="categoria_seq", sequenceName="categoria_seq")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="categoria_seq")
	private Long id;

	@NotEmpty
	@Column(length=50, nullable=false)
	private String nome;

	@Column(nullable=false)
	private boolean ativo;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="id_categoria_pai")
	private Categoria pai;

	@Column(length=100)
	private String titulo;

	@Column(length=1000)
	private String descricao;

	private boolean curso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_atualizacao")
	private Date dataAtualizacao;

	@JsonBackReference
	@OneToMany(mappedBy="categoria", fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<AreaConhecimento> listaAreasConhecimento;

	@JsonBackReference
	@OneToMany(mappedBy="pai", fetch=FetchType.EAGER)
	private List<Categoria> subCategorias;

	public Categoria() {
		super();
		this.ativo = true;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Categoria getPai() {
		return this.pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
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

	public boolean isCurso() {
		return this.curso;
	}

	public void setCurso(boolean curso) {
		this.curso = curso;
	}

	public List<AreaConhecimento> getListaAreasConhecimento() {
		return this.listaAreasConhecimento;
	}

	public void setListaAreasConhecimento(
			List<AreaConhecimento> listaAreasConhecimento) {
		this.listaAreasConhecimento = listaAreasConhecimento;
	}


	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}



	public List<Categoria> getSubCategorias() {
		return this.subCategorias;
	}

	public void setSubCategorias(List<Categoria> subCategorias) {
		this.subCategorias = subCategorias;
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
		Categoria other = (Categoria) obj;
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
