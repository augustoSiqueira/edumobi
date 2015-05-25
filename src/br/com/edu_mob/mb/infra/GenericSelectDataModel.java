package br.com.edu_mob.mb.infra;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class GenericSelectDataModel<T> extends ListDataModel<T> implements SelectableDataModel<T> {

	public GenericSelectDataModel() {}

	public GenericSelectDataModel(List<T> data) {
		super(data);
	}

	@Override
	public T getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<T> lista = (List<T>) this.getWrappedData();

		for (T e : lista) {
			if (e.toString().equals(rowKey)) {
				return e;
			}
		}

		return null;
	}


	@Override
	public Object getRowKey(T entity) {
		return entity;
	}

}
