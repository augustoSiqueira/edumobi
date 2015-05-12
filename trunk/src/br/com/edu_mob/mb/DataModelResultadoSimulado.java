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

import br.com.edu_mob.controller.ResultadoSimuladoController;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelResultadoSimulado extends LazyDataModel<ResultadoSimulado> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelPerfil.class.getName());

	private ResultadoSimuladoController resultadoSimuladoController;

	private List<ResultadoSimulado> listaResultadosSimulados;

	@Override
	public ResultadoSimulado getRowData(String rowKey) {
		for (ResultadoSimulado resultadoSimulado : this.listaResultadosSimulados) {
			if (resultadoSimulado.getId().equals(rowKey)) {
				return resultadoSimulado;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ResultadoSimulado resultadoSimulado) {
		return resultadoSimulado.getId();
	}

	@Override
	public List<ResultadoSimulado> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.resultadoSimuladoController = webAppContext.getBean("resultadoSimuladoController", ResultadoSimuladoController.class);
		Filter filtro = new Filter();
		filtro.put("idSimulado", 	facesContext.getExternalContext().getSessionMap().get("idSimulado").toString());
		try {
			filtro.putAll(filters);
			this.listaResultadosSimulados = this.resultadoSimuladoController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.resultadoSimuladoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaResultadosSimulados;
	}

}
