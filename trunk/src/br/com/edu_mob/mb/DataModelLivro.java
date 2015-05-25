package br.com.edu_mob.mb;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.edu_mob.controller.LivroController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Livro;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.UtilSession;

@ManagedBean
@ViewScoped
public class DataModelLivro extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelLivro.class.getName());

	private LivroController livroController;

	private List<Livro> listaLivros;
	
	private Categoria categoria;
	
	
	@Override
	public Livro getRowData(String rowKey) {
		for (Livro livro : this.listaLivros) {
			if (livro.getId().equals(rowKey)) {
				return livro;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Livro livro) {
		return livro.getId();
	}

	@Override
	public List<Livro> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.livroController = webAppContext.getBean("livroController", LivroController.class);
		Filter filtro = new Filter();
		try {
			categoria = (Categoria) UtilSession.getHttpSessionObject("categoriaId");
			filtro.putAll(filters);
			filtro.put("idCategoria",categoria.getId().toString());
			this.listaLivros = this.livroController.pesquisarPorFiltroPaginada(filtro, first, pageSize);
			this.setRowCount(this.livroController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaLivros;
	}
}
