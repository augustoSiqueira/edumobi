package br.com.edu_mob.mb.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.edu_mob.controller.UFController;
import br.com.edu_mob.entity.UF;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.GenericBean;

@FacesConverter("converterUF")
public class ConverterUF implements Converter {

	private static final Logger logger = Logger.getLogger(ConverterUF.class.getName());

	private UFController ufController;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		this.ufController = (UFController) GenericBean.getBean(context, "uFController");
		UF uf = null;
		try {
			if ((value != null) && !value.isEmpty()) {
				uf = this.ufController.pesquisarPorId(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return uf;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String retorno = null;
		if (object != null) {
			UF uf = ((UF) object);
			if (uf.getId() != null) {
				retorno = uf.getId().toString();
			}
		}
		return retorno;
	}

}
