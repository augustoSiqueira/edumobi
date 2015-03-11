package br.com.edu_mob.mb.infra;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.MensagemUtil;

@FacesValidator
public class RemoveEmBranco implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		if(value.toString().trim().isEmpty()){
			FacesMessage msg =
					new FacesMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
							MensagemUtil.getMensagem(ErrorMessage.DADOS_INVALIDOS.getChave()));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}

}
