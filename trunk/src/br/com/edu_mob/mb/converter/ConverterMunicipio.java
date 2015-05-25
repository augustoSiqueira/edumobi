package br.com.edu_mob.mb.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.edu_mob.controller.MunicipioController;
import br.com.edu_mob.entity.Municipio;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.GenericBean;

@FacesConverter("converterMunicipio")
public class ConverterMunicipio implements Converter {

	private static final Logger logger = Logger.getLogger(ConverterMunicipio.class.getName());

	private MunicipioController municipioController;

	private final static String SELECIONE = "Selecione";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		this.municipioController = (MunicipioController) GenericBean.getBean(context, "municipioController");
		Municipio municipio = null;
		try {
			if (((value != null) && !value.isEmpty()) && (!value.equals(SELECIONE))) {
				municipio = this.municipioController.pesquisarPorId(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return municipio;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String retorno = null;
		if (object != null) {
			Municipio municipio = ((Municipio) object);
			if (municipio.getId() != null) {
				retorno = municipio.getId().toString();
			}
		}
		return retorno;
	}


}
