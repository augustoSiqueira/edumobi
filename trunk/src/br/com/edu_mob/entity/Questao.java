package br.com.edu_mob.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
@SequenceGenerator(name="questao_seq", sequenceName="questao_seq")
public class Questao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="questao_seq")
	private Long id;

	
	@NotEmpty
	@Column(length=1000, nullable=false)
	private String enunciado;
	
	@NotEmpty
	private String observacao;
	

	
	@Column(length=500, nullable=true)
	private String caminhoImagem;
	
	
	
	
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
