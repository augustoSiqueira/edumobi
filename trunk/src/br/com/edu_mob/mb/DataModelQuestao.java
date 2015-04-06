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

import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelQuestao extends LazyDataModel<Questao> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelQuestao.class.getName());

	private QuestaoController questaoController;

	private List<Questao> listaQuestoes;

	@Override
	public Questao getRowData(String rowKey) {
		for (Questao questao : this.listaQuestoes) {
			if (questao.getId().equals(rowKey)) {
				return questao;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Questao questao) {
		return questao.getId();
	}

	@Override
	public List<Questao> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.questaoController = webAppContext.getBean("questaoController", QuestaoController.class);
		Filter filtro = new Filter();
		try {
			filtro.putAll(filters);
			this.listaQuestoes = this.questaoController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.questaoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaQuestoes;
	}
}
