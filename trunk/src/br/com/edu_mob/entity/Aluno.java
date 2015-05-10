package br.com.edu_mob.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.edu_mob.entity.enuns.Sexo;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class Aluno extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(length=11, nullable=false, unique=true)
	private String matricula;

	@Column(length=130)
	private String logradouro;

	@Column(length=70)
	private String bairro;

	@Column(length=8)
	private String cep;

	@Column(length=50)
	private String complemento;

	@Column(length=10)
	private String numero;

	@Temporal(TemporalType.DATE)
	@Column(name="data_nascimento")
	private Date dataNascimento;

	@Enumerated(EnumType.ORDINAL)
	private Sexo sexo;

	@Column(name="telefone_residencial", length=15)
	private String telefoneResidencial;

	@Column(name="telefone_celular",length=15)
	private String celular;

	@ManyToOne
	@JoinColumn(name="id_municipio")
	private Municipio municipio;

	@ManyToMany(fetch=FetchType.EAGER)
	private List<Categoria> cursos;
	
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return this.sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getTelefoneResidencial() {
		return this.telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<Categoria> getCursos() {
		return cursos;
	}

	public void setCursos(List<Categoria> cursos) {
		this.cursos = cursos;
	}
	
	
}
