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

import br.com.edu_mob.controller.PerfilController;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelPerfil extends LazyDataModel<Perfil> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelPerfil.class.getName());

	private PerfilController perfilController;

	private List<Perfil> listaPerfis;

	@Override
	public Perfil getRowData(String rowKey) {
		for (Perfil perfil : this.listaPerfis) {
			if (perfil.getId().equals(rowKey)) {
				return perfil;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Perfil perfil) {
		return perfil.getId();
	}

	@Override
	public List<Perfil> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.perfilController = webAppContext.getBean("perfilController", PerfilController.class);
		Filter filtro = new Filter();
		try {
			filtro.putAll(filters);
			this.listaPerfis = this.perfilController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.perfilController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaPerfis;
	}
}
