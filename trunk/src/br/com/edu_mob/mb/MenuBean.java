package br.com.edu_mob.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.util.AliasNavigation;

@ManagedBean
@SessionScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static long ID_ALUNO = 2L;

	public String pagePrincipal() {
		return AliasNavigation.PAGINA_PRINCIPAL;
	}
	public String getNomeUsuarioLogado(){
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String[] nome = usuario.getNome().split(" ");
		return nome[0];
	}

	public String pageDadosPessoais() {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(usuario.getPerfil().getId().equals(ID_ALUNO)) {
			return AliasNavigation.PAGINA_DADOS_PESSOAIS_ALUNO;
		}
		return AliasNavigation.PAGINA_DADOS_PESSOAIS_USUARIO;
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

	public String pageAreaConhecimentos() {
		return AliasNavigation.PAGINA_AREA_CONHECIMENTO;
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
	
	public String pageCurso() {
		 long id = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));  
		 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cursoId",id); 
		 return AliasNavigation.PAGINA_CURSO;
	}
	
	public String pageSimuladoDescricao() {
		return AliasNavigation.PAGINA_SIMULADO_DESCRICAO;
	}
}
