package br.com.edu_mob.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.controller.RespostaSimuladoController;
import br.com.edu_mob.controller.SimuladoDescricaoController;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.InfoMessage;
import br.com.edu_mob.util.AliasNavigation;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;
import br.com.edu_mob.util.Util;

@ManagedBean
@ViewScoped
public class RespostaSimuladoBean extends GenericBean implements Serializable {

	private static final Logger logger = Logger.getLogger(RespostaSimuladoBean.class.getName());

	private static final long serialVersionUID = 1L;

	private SimuladoDescricaoController simuladoDescricaoController;

	private RespostaSimuladoController respostaSimuladoController;

	private QuestaoController questaoController;

	private List<Questao> listaQuestoes;

	private Alternativa alternativa;

	private Questao questaoPainel;

	private Questao questao;

	private RespostaSimulado respostaSimulado;

	private Simulado simulado;

	private Calendar duracao;

	private Date dataInicio;

	private String horario;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Filter filtro = new Filter();
		this.respostaSimuladoController = (RespostaSimuladoController) this.getBean("respostaSimuladoController", RespostaSimuladoController.class);
		this.simuladoDescricaoController = (SimuladoDescricaoController) this.getBean("simuladoDescricaoController", SimuladoDescricaoController.class);
		this.questaoController = (QuestaoController) this.getBean("questaoController", QuestaoController.class);
		try {
			this.simulado = this.simuladoDescricaoController.pesquisarPorId(Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idSimulado").toString()));
			filtro.put("listaAreasConhecimento", this.simulado.getAreasConhecimento());
			filtro.put("qtdQuestoes", String.valueOf(this.simulado.getQntQuestao()));
			this.listaQuestoes = this.formatarQuestoes(this.questaoController.pesquisarSimulado(filtro));
			this.duracao = Calendar.getInstance();
			this.duracao.setTime(this.simulado.getDuracao());
			this.horario = Util.converteData(this.duracao.getTime(), Util.FORMATO_HORA_PT_BR);
			this.dataInicio = new Date();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public String iniciarCronometro() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.duracao.add(Calendar.MINUTE, -1);
		this.horario =  Util.converteData(this.duracao.getTime(), Util.FORMATO_HORA_PT_BR);
		if((this.duracao.get(Calendar.HOUR) == 0) && (this.duracao.get(Calendar.MINUTE) == 0) && (this.duracao.get(Calendar.SECOND) == 0)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tempo Esgotado", MensagemUtil.getMensagem(InfoMessage.TEMPO_ESGOTADO.getChave())));
			this.horario =  Util.converteData(this.duracao.getTime(), Util.FORMATO_HORA_PT_BR);
			this.finalizarSimulado();
		}
		return null;
	}

	public void responder() {
		if((this.alternativa != null)) {
			if(this.alternativa.getCorreta()) {
				this.questao.setCorreta(true);
			} else {
				this.questao.setCorreta(false);
			}
			this.questao.setAlternativa(this.alternativa);
			DataTable dataQuestao = this.obterDataTable();
			dataQuestao.setFirst(this.questao.getNumero());
		}
	}

	public DataTable obterDataTable() {
		return (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":listaQuestoes:tabelaQuestoes");
	}

	public String finalizarSimulado() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ResultadoSimulado resultadoSimulado = new ResultadoSimulado();
		resultadoSimulado.setDataHoraInicio(this.dataInicio);
		resultadoSimulado.setDataHoraFim(new Date());
		resultadoSimulado.setTempoTotal(Util.calcularDiferencaEntreDatas(resultadoSimulado.getDataHoraInicio(), resultadoSimulado.getDataHoraFim()));
		resultadoSimulado.setUsuario(usuario);
		resultadoSimulado.setSimulado(this.simulado);
		try {
			this.respostaSimuladoController.salvar(this.listaQuestoes, this.simulado, resultadoSimulado, usuario);
			context.getExternalContext().redirect(AliasNavigation.PAGINA_RESULTADOS_SIMULADO + "&idSimulado=" + this.simulado.getId());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}  catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage(), e1);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e1.getMessage());
		}
		return null;
	}

	public List<Questao> formatarQuestoes(List<Questao> listaQuestao) {
		String letras[] = {"A", "B", "C", "D", "E"};
		int numero = 1;
		if(((listaQuestao != null) && !listaQuestao.isEmpty())) {
			for (Questao questao : listaQuestao) {
				questao.setNumero(numero++);
				for (int i = 0; i < questao.getListaAlternativas().size(); i++) {
					questao.getListaAlternativas().get(i).setResposta(letras[i] + " - " + questao.getListaAlternativas().get(i).getResposta());
				}
			}
		}
		return listaQuestao;
	}

	public void atualizarQuestao() {
		DataTable dataQuestao = this.obterDataTable();
		dataQuestao.setFirst(this.questaoPainel.getNumero() - 1);
		if((this.questaoPainel.getAlternativa() != null) && (this.questaoPainel.getAlternativa().getId() != null)) {
			this.alternativa = this.questaoPainel.getAlternativa();
		}
	}

	public List<Questao> getListaQuestoes() {
		return this.listaQuestoes;
	}

	public void setListaQuestoes(List<Questao> listaQuestoes) {
		this.listaQuestoes = listaQuestoes;
	}

	public Alternativa getAlternativa() {
		return this.alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public RespostaSimulado getRespostaSimulado() {
		return this.respostaSimulado;
	}

	public void setRespostaSimulado(RespostaSimulado respostaSimulado) {
		this.respostaSimulado = respostaSimulado;
	}

	public Simulado getSimulado() {
		return this.simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}

	public Questao getQuestaoPainel() {
		return this.questaoPainel;
	}

	public void setQuestaoPainel(Questao questaoPainel) {
		this.questaoPainel = questaoPainel;
	}

	public String getHorario() {
		return this.horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Calendar getDuracao() {
		return this.duracao;
	}

	public void setDuracao(Calendar duracao) {
		this.duracao = duracao;
	}

}
