package br.com.edu_mob.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MensagemUtil {

	public static final String PACOTE_MENSAGENS_IDIOMAS = "br.com.edu_mob.message.messages";

	public static String getMensagem(String propriedade) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(propriedade);
	}

	public static String getMensagem(String propriedade, Object... parametros) {
		String mensagem = getMensagem(propriedade);
		MessageFormat messageFormat = new MessageFormat(mensagem);
		return messageFormat.format(parametros);
	}

	public static String getMensagem(Locale locale, String propridade) {
		ResourceBundle bundle = ResourceBundle.getBundle(MensagemUtil.PACOTE_MENSAGENS_IDIOMAS, locale);
		return bundle.getString(propridade);
	}

	public static String getMensagem(Locale locale, String propriedade, Object... parametros) {
		String mensagem = getMensagem(locale, propriedade);
		MessageFormat messageFormat = new MessageFormat(mensagem);
		return messageFormat.format(parametros);
	}

}
