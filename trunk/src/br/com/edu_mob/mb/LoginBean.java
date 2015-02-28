package br.com.edu_mob.mb;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.security.AuthenticationService;
import br.com.edu_mob.util.AliasNavegation;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class LoginBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuthenticationService authenticationService;

	private String email;

	private String senha;

	public LoginBean() {
		this.authenticationService =
				(AuthenticationService) this.getBean("authenticationService", AuthenticationService.class);
	}

	public String login() {
		boolean success = this.authenticationService.login(this.email, this.senha);
		if (!success) {
			FacesMessage facesMessage =
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MensagemUtil.getMensagem(ErrorMessage.LOGIN_SENHA_INVALIDOS
							.getChave()));
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			return AliasNavegation.FALHA_LOGIN;
		}
		return AliasNavegation.PAGINA_PRINCIPAL;
	}

	public String logout() {
		this.authenticationService.logout();
		return AliasNavegation.LOGIN;
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



}
