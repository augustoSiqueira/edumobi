package br.com.edu_mob.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq")
@Inheritance(strategy=InheritanceType.JOINED)
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_seq")
	private Long id;


	@Column(length = 150, nullable = false)
	private String nome;


	@Column(length = 11, nullable = false)
	private String cpf;


	@Column(length = 100, nullable = false)
	private String email;

	@Column(length = 100, nullable = false)
	private String senha;

	@JsonIgnore
	@Column(nullable = false)
	private boolean ativo;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

	public Usuario() {
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

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@JsonIgnore
	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> listaGrantedAuthority = new ArrayList<GrantedAuthority>();
		for (Funcionalidade funcionalidade : this.perfil.getListaFuncionalidades()) {
			listaGrantedAuthority.add(new SimpleGrantedAuthority(funcionalidade.getRole()));
		}
		return listaGrantedAuthority;
	}

	@JsonIgnore
	@Override
	@Transient
	public String getPassword() {
		return this.senha;
	}

	@JsonIgnore
	@Override
	@Transient
	public String getUsername() {
		return this.email;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	@Transient
	public boolean isEnabled() {
		return this.ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.cpf == null) ? 0 : this.cpf.hashCode());
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
		Usuario other = (Usuario) obj;
		if (this.cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		} else if (!this.cpf.equals(other.cpf)) {
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

}
