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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.edu_mob.controller.ResultadoSimuladoController;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.entity.infra.ResultadoSimuladoDTO;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelResultadoSimulado extends LazyDataModel<ResultadoSimuladoDTO> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelPerfil.class.getName());

	private ResultadoSimuladoController resultadoSimuladoController;

	private List<ResultadoSimuladoDTO> listaResultadosSimuladoDTO;

	@Override
	public ResultadoSimuladoDTO getRowData(String rowKey) {
		for (ResultadoSimuladoDTO resultadoSimulado : this.listaResultadosSimuladoDTO) {
			if (resultadoSimulado.getId().equals(rowKey)) {
				return resultadoSimulado;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ResultadoSimuladoDTO resultadoSimuladoDTO) {
		return resultadoSimuladoDTO.getId();
	}

	@Override
	public List<ResultadoSimuladoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.resultadoSimuladoController = webAppContext.getBean("resultadoSimuladoController", ResultadoSimuladoController.class);
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Filter filtro = new Filter();
		filtro.put("idUsuario", usuario.getId().toString());
		filtro.put("idSimulado", facesContext.getExternalContext().getSessionMap().get("idSimulado").toString());
		try {
			filtro.putAll(filters);
			this.listaResultadosSimuladoDTO = this.resultadoSimuladoController.pesquisarPorFiltroPaginadaDTO(filtro, first, pageSize);
			this.setRowCount(this.resultadoSimuladoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaResultadosSimuladoDTO;
	}

}
