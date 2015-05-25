package br.com.edu_mob.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.edu_mob.entity.enuns.Letra;

@Entity
@Table
@SequenceGenerator(name="alternativa_seq", sequenceName="alternativa_seq")
public class Alternativa implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="alternativa_seq")
	private Long id;

	@Column(length = 200, nullable = false)
	private String resposta;

	@Column(nullable = false)
	private boolean correta;

	@Enumerated(EnumType.STRING)
	private Letra letra;

	@ManyToOne
	@JoinColumn(name = "id_questao")
	private Questao questao;

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getResposta(){
		return this.resposta;
	}

	public void setResposta(String resposta){
		this.resposta = resposta;
	}

	public boolean getCorreta(){
		return this.correta;
	}

	public void setCorreta(boolean correta){
		this.correta = correta;
	}

	public Letra getLetra() {
		return this.letra;
	}

	public void setLetra(Letra letra) {
		this.letra = letra;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
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
		Alternativa other = (Alternativa) obj;
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
