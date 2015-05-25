package br.com.edu_mob.mb.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.edu_mob.controller.PerfilController;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.GenericBean;

@FacesConverter("converterPerfil")
public class ConverterPerfil implements Converter {

	private static final Logger logger = Logger.getLogger(ConverterPerfil.class.getName());


	private PerfilController perfilController;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		this.perfilController = (PerfilController) GenericBean.getBean(context, "perfilController");
		Perfil perfil = null;
		try {
			if ((value != null) && !value.isEmpty()) {
				perfil = this.perfilController.pesquisarPorId(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return perfil;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String retorno = null;
		if (object != null) {
			Perfil perfil = ((Perfil) object);
			if (perfil.getId() != null) {
				retorno = perfil.getId().toString();
			}
		}
		return retorno;
	}

}
