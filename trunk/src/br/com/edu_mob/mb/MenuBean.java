package br.com.edu_mob.mb;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;

import br.com.edu_mob.util.AliasNavigation;

@javax.faces.bean.ManagedBean
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
}
