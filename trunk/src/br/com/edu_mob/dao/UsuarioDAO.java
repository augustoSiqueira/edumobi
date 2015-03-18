package br.com.edu_mob.dao;

import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.DAOException;

public interface UsuarioDAO extends GenericDAO, PesquisaDAO<Usuario> {
	
	Usuario pesquisarPorEmail(String email) throws DAOException;
}
