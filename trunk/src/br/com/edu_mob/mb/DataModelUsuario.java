package br.com.edu_mob.mb;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.Util;

@ManagedBean
@ViewScoped
public class DataModelUsuario extends LazyDataModel<Usuario> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelUsuario.class.getName());

	private UsuarioController usuarioController;

	private List<Usuario> listaUsuarios;

	@Override
	public Usuario getRowData(String rowKey) {
		for (Usuario usuario : this.listaUsuarios) {
			if (usuario.getId().equals(rowKey)) {
				return usuario;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Usuario usuario) {
		return usuario.getId();
	}

	@Override
	public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.usuarioController = webAppContext.getBean("usuarioController", UsuarioController.class);
		Filter filtro = new Filter();
		try {
			filtro.putAll(filters);
			this.listaUsuarios = this.usuarioController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			if ((this.listaUsuarios != null) && !this.listaUsuarios.isEmpty()) {
				this.listaUsuarios.forEach(u -> Util.formatarCPF(u.getCpf()));
			}
			this.setRowCount(this.usuarioController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaUsuarios;
	}
}
