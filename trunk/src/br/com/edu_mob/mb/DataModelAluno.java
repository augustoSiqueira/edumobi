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

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.Util;

@ManagedBean
@ViewScoped
public class DataModelAluno extends LazyDataModel<Aluno> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelAluno.class.getName());

	private AlunoController alunoController;

	private List<Aluno> listaAlunos;

	@Override
	public Aluno getRowData(String rowKey) {
		for (Aluno aluno : this.listaAlunos) {
			if (aluno.getId().equals(rowKey)) {
				return aluno;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Aluno aluno) {
		return aluno.getId();
	}

	@Override
	public List<Aluno> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.alunoController = webAppContext.getBean("alunoController", AlunoController.class);
		Filter filtro = new Filter();
		try {
			filtro.putAll(filters);
			this.listaAlunos = this.alunoController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			if ((this.listaAlunos != null) && !this.listaAlunos.isEmpty()) {
				this.listaAlunos.forEach(a -> a.setCpf(Util.formatarCPF(a.getCpf())));
			}
			this.setRowCount(this.alunoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaAlunos;
	}

}
