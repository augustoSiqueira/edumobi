package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.security.AuthenticationService;
import br.com.edu_mob.util.AliasNavigation;
import br.com.edu_mob.util.MensagemUtil;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class LoginBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuthenticationService authenticationService;

	private String email;

	private String senha;
	
	private String emailRecuperar;
	
	private UsuarioController usuarioController;

	public LoginBean() {
		this.authenticationService =
				(AuthenticationService) this.getBean("authenticationService", AuthenticationService.class);
		this.usuarioController = (UsuarioController) this.getBean("usuarioController", UsuarioController.class);
	}

	public String login() {
		boolean success = this.authenticationService.login(this.email, this.senha);
		if (!success) {
			FacesMessage facesMessage =
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MensagemUtil.getMensagem(ErrorMessage.LOGIN_SENHA_INVALIDOS
							.getChave()));
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			return AliasNavigation.FALHA_LOGIN;
		}
		return AliasNavigation.PAGINA_PRINCIPAL;
	}

	public String logout() {
		this.authenticationService.logout();
		return AliasNavigation.LOGIN;
	}

	public String recuperarSenha(){
		try {
			usuarioController.recuperarSenha(emailRecuperar);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.SENHA_REC.getValor(),"");
			emailRecuperar = "";
		} catch (RNException | DAOException e) {
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		
		}
		
		return null;
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

	public String getEmailRecuperar() {
		return emailRecuperar;
	}

	public void setEmailRecuperar(String emailRecuperar) {
		this.emailRecuperar = emailRecuperar;
	}

	public UsuarioController getUsuarioController() {
		return usuarioController;
	}

	public void setUsuarioController(UsuarioController usuarioController) {
		this.usuarioController = usuarioController;
	}

	
}
