package br.com.edu_mob.mb;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

public class GenericBean {

	/** Filtro de pesquisa. */
	protected Filter filtro;


	/**
	 * Cria uma nova inst�ncia desta classe.
	 */
	public GenericBean() {
		this.filtro = new Filter();
	}

	/**
	 * Retorna o filtro de consulta.
	 *
	 * @return Uma inst�ncia de Filter.
	 */
	public Filter getFiltro() {
		return this.filtro;
	}

	protected Object getBean(String beanName, Class<?> classe) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		return webAppContext.getBean(beanName, classe);

	}

	protected Object getBean(String beanName) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		return webAppContext.getBean(beanName);

	}


	/**
	 * Retorna o n�mero total de mensagens a serem exibidas na tela.
	 *
	 * @return O n�mero total de mensagens a serem exibidas na tela.
	 */
	public int getTotalMensagens() {
		int total = 0;

		for (Iterator<javax.faces.application.FacesMessage> it = FacesContext.getCurrentInstance().getMessages(); it
				.hasNext();) {
			it.next();
			++total;
		}
		return total;
	}

	public void addMessage(String titulo, String message, String... parametros) {
		FacesMessage msg = null;
		if (message.indexOf("info_") > -1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, MensagemUtil.getMensagem(message, parametros));
		} else if (message.indexOf("erro_") > -1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, MensagemUtil.getMensagem(message, parametros));
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, MensagemUtil.getMensagem(message, parametros));
		}
		FacesContext.getCurrentInstance().addMessage(titulo, msg);
	}

	public void addMessage(String titulo, List<String> listaMensagens, String... parametros) {
		FacesMessage msg = null;
		if ((listaMensagens != null) && !listaMensagens.isEmpty()) {
			for (String message : listaMensagens) {
				if (message.indexOf("info_") > -1) {
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, MensagemUtil.getMensagem(message, parametros));
				} else if (message.indexOf("erro_") > -1) {
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, MensagemUtil.getMensagem(message, parametros));
				} else if(message.indexOf("alerta_") > -1) {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, MensagemUtil.getMensagem(message, parametros));
				}
				FacesContext.getCurrentInstance().addMessage(titulo, msg);
			}
		}
	}

	public void addMessage(String message) {
		FacesMessage msg = null;
		if (message.indexOf("info_") > -1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, MensagemUtil.getMensagem(message));
		} else if (message.indexOf("erro_") > -1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, MensagemUtil.getMensagem(message));
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, MensagemUtil.getMensagem(message));
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	// public void addMessage(InvalidStateException exception) {
	// InvalidValue[] values = exception.getInvalidValues();
	// for (int i = 0; i < values.length; i++) {
	// this.addMessage(values[i].getMessage());
	// }
	// }

	public String getMessage(String msg, Locale locale) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		return webAppContext.getMessage(msg, null, locale);
	}

	public String getMessage(String msg) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		return webAppContext.getMessage(msg, null, null);
	}

	public static Object getBean(FacesContext context, String beanName) {
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(context);
		return webAppContext.getBean(beanName);

	}

	public static String getMessage(FacesContext context, String msg) {
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(context);
		return webAppContext.getMessage(msg, null, null);
	}

	public static void addMessage(FacesContext context, String message) {
		FacesMessage msg = null;
		if (message.indexOf("info_") > -1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, getMessage(context, message));
		} else if (message.indexOf("erro_") > -1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, getMessage(context, message));
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, getMessage(context, message));
		}
		context.addMessage(null, msg);
	}

	/**
	 * Get DataTable do primefaces
	 *
	 * @param nome
	 * @return
	 */
	protected UIData getTabela(String formId, String dataTableId) {
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		UIForm form = (UIForm) view.findComponent(formId);
		UIData data = (UIData) form.findComponent(dataTableId);
		return data;
	}

	/**
	 * Método para redirecionar as paginas.
	 *
	 * @param pagina
	 */
	protected void redirect(String pagina) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
		nh.handleNavigation(facesContext, null, pagina);
	}

}
