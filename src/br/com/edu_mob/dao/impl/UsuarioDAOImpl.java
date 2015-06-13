package br.com.edu_mob.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.UsuarioDAO;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;
import br.com.edu_mob.util.Util;

@Repository("usuarioDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioDAOImpl extends GenericDAOImpl implements UsuarioDAO {

	private static final Logger logger = Logger.getLogger(UsuarioDAOImpl.class.getName());

	@Autowired
	public UsuarioDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Usuario> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<Usuario> listaUsuarios = null;
		String cpf = filtro.getAsString("cpf");
		String nome = filtro.getAsString("nome");
		String email = filtro.getAsString("email");
		String senha = filtro.getAsString("senha");
		Boolean ativo = (Boolean) filtro.get("ativo");
		String idPerfil = (String) filtro.get("idPerfil");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Usuario.class);
			if ((cpf != null) && !cpf.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("cpf", Util.removerCaracteresEspeciais(cpf)));
			}
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if ((email != null) && !email.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("email", email));
			}
			if ((idPerfil != null) && !idPerfil.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("perfil.id", Long.parseLong(idPerfil)));
			}
			if ((senha != null) && !senha.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("senha", Util.criptografar(senha)));
			}
			if (ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaUsuarios = this.findByCriteria(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUsuarios;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int retorno = -1;
		String cpf = filtro.getAsString("cpf");
		String nome = filtro.getAsString("nome");
		String email = filtro.getAsString("email");
		String senha = filtro.getAsString("senha");
		Boolean ativo = (Boolean) filtro.get("ativo");
		String idPerfil = (String) filtro.get("idPerfil");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Usuario.class);
			if ((cpf != null) && !cpf.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("cpf", Util.removerCaracteresEspeciais(cpf)));
			}
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if ((email != null) && !email.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("email", email));
			}
			if ((idPerfil != null) && !idPerfil.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("perfil.id", Long.parseLong(idPerfil)));
			}
			if ((senha != null) && !senha.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("senha", Util.criptografar(senha)));
			}
			if (ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			retorno = this.getDataCount(detachedCriteria);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return retorno;
	}

	@Override
	public List<Usuario> pesquisarPorFiltroPaginada(Filter filtro, int primeiroReg, int paginaSize) throws DAOException {
		List<Usuario> listaUsuarios = null;
		String cpf = filtro.getAsString("cpf");
		String nome = filtro.getAsString("nome");
		String email = filtro.getAsString("email");
		Boolean ativo = (Boolean) filtro.get("ativo");
		String idPerfil = (String) filtro.get("idPerfil");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Usuario.class);
			if ((cpf != null) && !cpf.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("cpf", Util.removerCaracteresEspeciais(cpf)));
			}
			if ((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if ((email != null) && !email.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("email", email));
			}
			if ((idPerfil != null) && !idPerfil.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("perfil.id", Long.parseLong(idPerfil)));
			}
			if (ativo != null) {
				detachedCriteria.add(Restrictions.eq("ativo", ativo));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaUsuarios = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(MensagemUtil.getMensagem(ErrorMessage.DAO.getChave()));
		}
		return listaUsuarios;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id) throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Usuario.class);
				detachedCriteria.add(Restrictions.eq(campo, valor).ignoreCase());
				if (id != null) {
					detachedCriteria.add(Restrictions.ne("id", id));
				}
				existe = !this.findByCriteria(detachedCriteria).isEmpty();
			}
			return existe;
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
	}
	
	@Override
	public Usuario pesquisarPorEmail(String email) throws DAOException{
		List<Usuario> listaUsuarios = null;
		Usuario usuario = null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Usuario.class);
		try{
			if (email != null && !email.isEmpty()) {
				
				detachedCriteria.add(Restrictions.eq("email", email).ignoreCase());			
			}
			listaUsuarios = this.findByCriteria(detachedCriteria);
			
			if(listaUsuarios.size()== 1){
				usuario = listaUsuarios.get(0);
			}
			return usuario;		
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
	}

}
