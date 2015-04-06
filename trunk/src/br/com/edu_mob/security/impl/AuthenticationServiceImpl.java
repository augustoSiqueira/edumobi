package br.com.edu_mob.security.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.security.AuthenticationService;

@Component("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger logger = Logger.getLogger(AuthenticationServiceImpl.class.getName());

	@Autowired(required = true)
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Override
	public boolean login(String email, String senha) {
		boolean valido = false;
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, senha);
			Authentication authenticate = this.authenticationManager.authenticate(token);
			if (authenticate.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authenticate);
				valido = true;
			}
		} catch (AuthenticationException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return valido;
	}


	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		this.invalidateSession();
	}


	@Override
	public Usuario getUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


	@Override
	public void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
	}

}
