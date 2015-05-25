package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.controller.RespostaEstudoController;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.RespostaEstudo;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.MensagemUtil;
import br.com.edu_mob.util.UtilSession;

@ManagedBean
@ViewScoped
public class RespostaEstudoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RespostaEstudoBean.class.getName());

	@ManagedProperty(value = "#{dataModelRespostaEstudo}")
	private DataModelRespostaEstudo dataModelRespostaEstudo;

	private RespostaEstudoController respostaEstudoController;

	private AreaConhecimentoController areaConhecimentoController;

	private RespostaEstudo respostaEstudo;

	private Alternativa alternativa;

	private AreaConhecimento areaConhecimento;

	private Questao questao;

	private Boolean correta;

	private Boolean errada;

	private Boolean exibirComentario;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.respostaEstudoController = (RespostaEstudoController) this.getBean("respostaEstudoController", RespostaEstudoController.class);
		this.areaConhecimentoController = (AreaConhecimentoController) this.getBean("areaConhecimentoController", AreaConhecimentoController.class);
		try {
			UtilSession.getHttpSession().setAttribute("idAreaConhecimento", Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idAreaConhecimento")));
			this.areaConhecimento = this.areaConhecimentoController.pesquisarPorId(Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idAreaConhecimento").toString()));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
		this.respostaEstudo = new RespostaEstudo();
	}

	public void responder() {
		if((this.alternativa != null) && this.alternativa.getCorreta()) {
			this.questao.setCorreta(true);
		} else {
			this.questao.setCorreta(false);
		}
		this.incluir(this.questao);
	}

	public void incluir(Questao questao) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			this.respostaEstudo.setCategoria(this.areaConhecimento.getCategoria());
			this.respostaEstudo.setAreaConhecimento(questao.getAreaConhecimento());
			this.respostaEstudo.setQuestao(questao);
			this.respostaEstudo.setCorreta(questao.getCorreta());
			this.respostaEstudo.setDataHora(new Date());
			this.respostaEstudo.setUsuario(usuario);
			this.respostaEstudoController.salvar(this.respostaEstudo);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void exibirComentario() {
		this.questao.setExibirComentario(true);
	}

	public DataModelRespostaEstudo getDataModelRespostaEstudo() {
		return this.dataModelRespostaEstudo;
	}

	public void setDataModelRespostaEstudo(DataModelRespostaEstudo dataModelRespostaEstudo) {
		this.dataModelRespostaEstudo = dataModelRespostaEstudo;
	}

	public RespostaEstudo getRespostaEstudo() {
		return this.respostaEstudo;
	}

	public void setRespostaEstudo(RespostaEstudo respostaEstudo) {
		this.respostaEstudo = respostaEstudo;
	}

	public Alternativa getAlternativa() {
		return this.alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	public Boolean getCorreta() {
		return this.correta;
	}

	public void setCorreta(Boolean correta) {
		this.correta = correta;
	}

	public Boolean getExibirComentario() {
		return this.exibirComentario;
	}

	public void setExibirComentario(Boolean exibirComentario) {
		this.exibirComentario = exibirComentario;
	}

	public Boolean getErrada() {
		return this.errada;
	}

	public void setErrada(Boolean errada) {
		this.errada = errada;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public AreaConhecimento getAreaConhecimento() {
		return this.areaConhecimento;
	}

	public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
		this.areaConhecimento = areaConhecimento;
	}

}
