package br.com.edu_mob.security;

import br.com.edu_mob.entity.Usuario;

public interface AuthenticationService {

	boolean login(String email, String senha);

	void logout();

	void invalidateSession();

	Usuario getUsuarioLogado();

}
