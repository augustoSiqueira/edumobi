package br.com.edu_mob.mb.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.GenericBean;

@FacesConverter("converterCategoria")
public class ConverterCategoria implements Converter {

	private static final Logger logger = Logger.getLogger(ConverterCategoria.class.getName());

	private CategoriaController categoriaController;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		this.categoriaController = (CategoriaController) GenericBean.getBean(context, "categoriaController");
		Categoria categoria = null;
		try {
			if ((value != null) && !value.isEmpty()) {
				categoria = this.categoriaController.pesquisarPorId(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String retorno = null;
		if (object != null) {
			Categoria categoria = ((Categoria) object);
			if (categoria.getId() != null) {
				retorno = categoria.getId().toString();
			}
		}
		return retorno;
	}

}
