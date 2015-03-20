package br.com.edu_mob.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.edu_mob.util.AliasNavigation;

@ManagedBean
@SessionScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String pagePrincipal() {
		return AliasNavigation.PAGINA_PRINCIPAL;
	}

	public String pageUsuarios() {
		return AliasNavigation.PAGINA_USUARIO;
	}

	public String pagePerfis() {
		return AliasNavigation.PAGINA_PERFIL;
	}

	public String pageCategorias() {
		return AliasNavigation.PAGINA_CATEGORIA;
	}
	
	
	public String pageQuestao() {
		return AliasNavigation.PAGINA_QUESTAO;
	}

	public String pageCadastrarAluno() {
		return AliasNavigation.PAGINA_CADASTRO_ALUNO;
	}

	public String pageAlunos() {
		return AliasNavigation.PAGINA_ALUNO;
	}
}
