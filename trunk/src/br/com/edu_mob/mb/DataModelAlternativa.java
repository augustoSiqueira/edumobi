package br.com.edu_mob.mb;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.edu_mob.entity.Alternativa;

@ManagedBean
@ViewScoped
public class DataModelAlternativa extends LazyDataModel<Alternativa> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DataModelAlternativa.class.getName());

	private List<Alternativa> listaAlternativa;
	
	@Override
	public Alternativa getRowData(String rowKey) {
		for (Alternativa alternativa : this.listaAlternativa) {
			if (alternativa.getId().equals(rowKey)) {
				return alternativa;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Alternativa alternativa) {
		return alternativa.getId();
	}

	@Override
	public List<Alternativa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return this.listaAlternativa;
	}
	
}