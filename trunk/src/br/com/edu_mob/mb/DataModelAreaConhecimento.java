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

import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.UtilSession;

@ManagedBean
@ViewScoped
public class DataModelAreaConhecimento extends LazyDataModel<AreaConhecimento>{

	private static final long serialVersionUID = 7059544517382198399L;

	private static final Logger logger = Logger.getLogger(DataModelAreaConhecimento.class.getName());

	private AreaConhecimentoController areaConhecimentoController;

	private List<AreaConhecimento> listaAreaConhecimento;
	
	
	@Override
	public AreaConhecimento getRowData(String rowKey) {
		for (AreaConhecimento areaConhecimento : this.listaAreaConhecimento) {
			if (areaConhecimento.getId().equals(rowKey)) {
				return areaConhecimento;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AreaConhecimento areaConhecimento) {
		return areaConhecimento.getId();
	}
	
	@Override
	public List<AreaConhecimento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.areaConhecimentoController = webAppContext.getBean("areaConhecimentoController", AreaConhecimentoController.class);
		Filter filtro = new Filter();
		try {
			filtro.put("idCategoria", ((Categoria) UtilSession.getHttpSessionObject("categoriaSelecionada")).getId().toString());
			filtro.putAll(filters);
			this.listaAreaConhecimento = this.areaConhecimentoController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.areaConhecimentoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaAreaConhecimento;
	}
}
