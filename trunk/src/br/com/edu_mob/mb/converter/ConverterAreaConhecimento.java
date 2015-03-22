package br.com.edu_mob.mb.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.GenericBean;

@FacesConverter("converterAreaConhecimento")
public class ConverterAreaConhecimento implements Converter{

	private static final Logger logger = Logger.getLogger(ConverterQuestao.class.getName());

	private AreaConhecimentoController areaConhecimentoController;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		this.areaConhecimentoController = (AreaConhecimentoController) GenericBean.getBean(context, "areaConhecimentoController");
		AreaConhecimento areaConhecimento = null;
		try {
			if ((value != null) && !value.isEmpty()) {
				areaConhecimento = this.areaConhecimentoController.pesquisarPorId(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return areaConhecimento;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String retorno = null;
		if (object != null) {
			AreaConhecimento areaConhecimento = ((AreaConhecimento) object);
			if (areaConhecimento.getId() != null) {
				retorno = areaConhecimento.getId().toString();
			}
		}
		return retorno;
	}

	
}
