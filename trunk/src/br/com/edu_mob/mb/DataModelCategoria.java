package br.com.edu_mob.mb;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelCategoria extends LazyDataModel<Categoria> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelCategoria.class.getName());

	private CategoriaController categoriaController;

	private List<Categoria> listaCategorias;

	@Override
	public Categoria getRowData(String rowKey) {
		for (Categoria categoria : this.listaCategorias) {
			if (categoria.getId().equals(rowKey)) {
				return categoria;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Categoria categoria) {
		return categoria.getId();
	}

	@Override
	public List<Categoria> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.categoriaController = webAppContext.getBean("categoriaController", CategoriaController.class);
		Filter filtro = new Filter();
		try {
			filtro.putAll(filters);
			this.listaCategorias = this.categoriaController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.categoriaController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaCategorias;
	}
}
