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

import br.com.edu_mob.controller.SimuladoDescricaoController;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelSimuladoDescricao extends LazyDataModel<Simulado> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelSimuladoDescricao.class.getName());

	private SimuladoDescricaoController simuladoDescricaoController;

	private List<Simulado> listaSimulado;
	
		
	
	@Override
	public Simulado getRowData(String rowKey) {
		for (Simulado simulado : this.listaSimulado) {
			if (simulado.getId().equals(rowKey)) {
				return simulado;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Simulado simulado) {
		return simulado.getId();
	}

	@Override
	public List<Simulado> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.simuladoDescricaoController = webAppContext.getBean("simuladoDescricaoController", SimuladoDescricaoController.class);
		
		Filter filtro = new Filter();
		try {
			filtro.putAll(filters);
			this.listaSimulado = this.simuladoDescricaoController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.simuladoDescricaoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaSimulado;
	}
}
