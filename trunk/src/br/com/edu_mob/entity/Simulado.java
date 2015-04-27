package br.com.edu_mob.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SequenceGenerator(name="simulado_desc_seq", sequenceName="simulado_desc_seq")
public class Simulado {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="simulado_desc_seq")
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String titulo;
	@Column(length = 255, nullable = false)
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="tempo")
	private Date tempo;
	
	private int qnt_questao;
	
	@ManyToOne
	private Categoria categoria;
	
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<AreaConhecimento> areasConhecimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_atualizacao")
	private Date dataAtualizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getTempo() {
		return tempo;
	}

	public void setTempo(Date tempo) {
		this.tempo = tempo;
	}

	public int getQnt_questao() {
		return qnt_questao;
	}

	public void setQnt_questao(int qnt_questao) {
		this.qnt_questao = qnt_questao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<AreaConhecimento> getAreasConhecimento() {
		return areasConhecimento;
	}

	public void setAreasConhecimento(List<AreaConhecimento> areasConhecimento) {
		this.areasConhecimento = areasConhecimento;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	
	
	
}
