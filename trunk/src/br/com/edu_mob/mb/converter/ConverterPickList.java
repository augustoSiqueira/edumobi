package br.com.edu_mob.mb.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.edu_mob.entity.Funcionalidade;

@FacesConverter("primeFacesPickListConverter")
public class ConverterPickList implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent uiComponent, String arg2) {
		Object result = null;
		if (uiComponent instanceof PickList) {
			Object dualList = ((PickList) uiComponent).getValue();
			DualListModel dualListModel = (DualListModel) dualList;
			for (Object object : dualListModel.getSource()) {
				String id = "" + ((Funcionalidade) object).getId();
				if (arg2.equals(id)) {
					result = object;
					break;
				}
			}
			if (result == null) {
				for (Object object : dualListModel.getTarget()) {
					String id = "" + ((Funcionalidade) object).getId();
					if (arg2.equals(id)) {
						result = object;
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent uiComponent, Object object) {
		String str = "";
		if (object instanceof Funcionalidade) {
			str = "" + ((Funcionalidade) object).getId();
		}
		return str;
	}

}
