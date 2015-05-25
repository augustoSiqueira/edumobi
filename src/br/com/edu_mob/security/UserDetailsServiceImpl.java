package br.com.edu_mob.security;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

	@Autowired
	private UsuarioController usuarioController;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
		Usuario usuario = null;
		Filter filtro = new Filter();
		filtro.put("email", email);
		List<Usuario> listaUsuarios = null;
		try {
			listaUsuarios = this.usuarioController.pesquisarPorFiltro(filtro);
			if ((listaUsuarios != null) && !listaUsuarios.isEmpty()) {
				usuario = listaUsuarios.get(0);
			} else {
				throw new UsernameNotFoundException(MensagemUtil.getMensagem(ErrorMessage.LOGIN_USUARIO_NAO_ENCONTRADO
						.getChave()));

			}
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return usuario;
	}

}
