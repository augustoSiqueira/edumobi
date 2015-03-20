package br.com.edu_mob.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.edu_mob.dao.AlunoDAO;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.Util;

@Repository("alunoDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class AlunoDAOImpl extends GenericDAOImpl implements AlunoDAO {

	private static final Logger logger = Logger.getLogger(AlunoDAOImpl.class.getName());

	@Autowired
	public AlunoDAOImpl(SessionFactory factory) {
		super(factory);
	}

	@Override
	public List<Aluno> pesquisarPorFiltro(Filter filtro) throws DAOException {
		List<Aluno> listaAlunos = null;
		String cpf = filtro.getAsString("cpf");
		String nome = filtro.getAsString("nome");
		String matricula = filtro.getAsString("matricula");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Aluno.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if((cpf != null) && !cpf.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("cpf", Util.removerCaracteresEspeciais(cpf)));
			}
			if((matricula != null) && !matricula.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("matricula", matricula));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaAlunos = this.findByCriteria(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaAlunos;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws DAOException {
		int count = 0;
		String cpf = filtro.getAsString("cpf");
		String nome = filtro.getAsString("nome");
		String matricula = filtro.getAsString("matricula");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Aluno.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if((cpf != null) && !cpf.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("cpf", Util.removerCaracteresEspeciais(cpf)));
			}
			if((matricula != null) && !matricula.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("matricula", matricula));
			}
			count = this.getDataCount(detachedCriteria);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return count;
	}

	@Override
	public List<Aluno> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws DAOException {
		List<Aluno> listaAlunos = null;
		String cpf = filtro.getAsString("cpf");
		String nome = filtro.getAsString("nome");
		String matricula = filtro.getAsString("matricula");

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Aluno.class);
			if((nome != null) && !nome.isEmpty()) {
				detachedCriteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			}
			if((cpf != null) && !cpf.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("cpf", Util.removerCaracteresEspeciais(cpf)));
			}
			if((matricula != null) && !matricula.isEmpty()) {
				detachedCriteria.add(Restrictions.eq("matricula", matricula));
			}
			detachedCriteria.addOrder(Order.asc("nome"));
			listaAlunos = this.buscarPaginada(detachedCriteria, primeiroReg, paginaSize);
		} catch(DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}
		return listaAlunos;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public long retornarUltimoID() throws DAOException {
		long id = 0L;
		StringBuilder sb = new StringBuilder();
		sb.append("select max(u.id) + 1 from Usuario u");
		try {
			id = (long) this.getHibernateTemplate().execute(
					new HibernateCallback() {
						@Override
						public Long doInHibernate(Session session) {
							org.hibernate.Query query = session.createQuery(sb
									.toString());
							return Long.parseLong(query.uniqueResult().toString());
						}
					});
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(ErrorMessage.DAO.getChave());
		}

		return id;
	}

	@Override
	public boolean verificarExistencia(String campo, String valor, Long id)
			throws DAOException {
		boolean existe = false;
		try {
			if (((campo != null) && !campo.isEmpty()) && ((valor != null) && !valor.isEmpty())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Aluno.class);
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

}
