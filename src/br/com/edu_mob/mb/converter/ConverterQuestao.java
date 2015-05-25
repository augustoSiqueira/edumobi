package br.com.edu_mob.mb.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.GenericBean;

@FacesConverter("converterQuestao")
public class ConverterQuestao implements Converter{

	private static final Logger logger = Logger.getLogger(ConverterQuestao.class.getName());

	private QuestaoController questaoController;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		this.questaoController = (QuestaoController) GenericBean.getBean(context, "questaoController");
		Questao questao = null;
		try {
			if ((value != null) && !value.isEmpty()) {
				questao = this.questaoController.pesquisarPorId(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return questao;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String retorno = null;
		if (object != null) {
			Questao questao = ((Questao) object);
			if (questao.getId() != null) {
				retorno = questao.getId().toString();
			}
		}
		return retorno;
	}

	
}
