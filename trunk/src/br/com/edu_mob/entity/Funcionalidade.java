package br.com.edu_mob.entity;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Funcionalidade implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@NotEmpty
	@Column(length = 30, nullable = false)
	private String role;

	@NotEmpty
	@Column(length = 100, nullable = false)
	private String descricao;

	@Column(nullable = false)
	private boolean exibir;

	@ManyToOne
	@JoinColumn(name = "id_funcionalidade_pai")
	private Funcionalidade funcionalidadePai;

	public Funcionalidade() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Funcionalidade getFuncionalidadePai() {
		return this.funcionalidadePai;
	}

	public void setFuncionalidadePai(Funcionalidade funcionalidadePai) {
		this.funcionalidadePai = funcionalidadePai;
	}

	public boolean isExibir() {
		return this.exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	@Override
	@Transient
	public String getAuthority() {
		return this.role;
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
		Funcionalidade other = (Funcionalidade) obj;
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
