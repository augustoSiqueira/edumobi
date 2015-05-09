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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
@SequenceGenerator(name="questao_seq", sequenceName="questao_seq")
public class Questao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="questao_seq")
	private Long id;

	@NotEmpty(message="Preencha o campo Enunciado.")
	@Column(length=1000, nullable=false)
	private String enunciado;

	@NotEmpty(message="Preencha o campo Comentário.")
	private String observacao;

	@Column(name="caminho_imagem",length=500, nullable=true)
	private String caminhoImagem;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_atualizacao")
	private Date dataAtualizacao;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "id_area_conhecimento")
	private AreaConhecimento areaConhecimento;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="questao")
	@Fetch(FetchMode.SUBSELECT)
	private List<Alternativa> listaAlternativas;

	@Transient
	private Boolean correta;

	@Transient
	private Alternativa alternativa;

	@Transient
	private Boolean exibirComentario;

	@Transient
	private int numero;

	@Column(name="nivel")
	private Integer nivel;

	public Questao() {
		super();
		this.correta = null;
		this.exibirComentario = null;
	}

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getEnunciado(){
		return this.enunciado;
	}

	public void setEnunciado(String enunciado){
		this.enunciado = enunciado;
	}

	public String getObservacao(){
		return this.observacao;
	}

	public void setObservacao(String observacao){
		this.observacao = observacao;
	}

	public String getCaminhoImagem(){
		return this.caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem){
		this.caminhoImagem = caminhoImagem;
	}

	public AreaConhecimento getAreaConhecimento(){
		return this.areaConhecimento;
	}

	public void setAreaConhecimento(AreaConhecimento areaConhecimento){
		this.areaConhecimento = areaConhecimento;
	}

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public List<Alternativa> getListaAlternativas() {
		return this.listaAlternativas;
	}

	public void setListaAlternativas(List<Alternativa> listaAlternativas) {
		this.listaAlternativas = listaAlternativas;
	}

	public Boolean getCorreta() {
		return this.correta;
	}

	public void setCorreta(Boolean correta) {
		this.correta = correta;
	}

	public Boolean getExibirComentario() {
		return this.exibirComentario;
	}

	public void setExibirComentario(Boolean exibirComentario) {
		this.exibirComentario = exibirComentario;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Integer getNivel(){
		return this.nivel;
	}

	public void setNivel(Integer nivel){
		this.nivel = nivel;
	}

	public Alternativa getAlternativa() {
		return this.alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
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
		Questao other = (Questao) obj;
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
