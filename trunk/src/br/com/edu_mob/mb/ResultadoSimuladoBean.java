package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.edu_mob.controller.RespostaSimuladoController;
import br.com.edu_mob.controller.ResultadoSimuladoController;
import br.com.edu_mob.controller.SimuladoDescricaoController;
import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class ResultadoSimuladoBean extends GenericBean implements Serializable {

	private static final Logger logger = Logger.getLogger(ResultadoSimuladoBean.class.getName());

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{dataModelResultadoSimulado}")
	private DataModelResultadoSimulado dataModelResultadoSimulado;

	private List<RespostaSimulado> listaRespostaSimulado;

	private ResultadoSimuladoController resultadoSimuladoController;

	private RespostaSimuladoController respostaSimuladoController;

	private SimuladoDescricaoController simuladoDescricaoController;

	private List<ResultadoSimulado> listaResultadoSimulado;

	private ResultadoSimulado resultadoSimulado;

	private Simulado simulado;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.resultadoSimuladoController = (ResultadoSimuladoController) this.getBean("resultadoSimuladoController", ResultadoSimuladoController.class);
		this.respostaSimuladoController = (RespostaSimuladoController) this.getBean("respostaSimuladoController", RespostaSimuladoController.class);
		this.simuladoDescricaoController = (SimuladoDescricaoController) this.getBean("simuladoDescricaoController", SimuladoDescricaoController.class);
		try {
			this.simulado = this.simuladoDescricaoController.pesquisarPorId(Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idSimulado").toString()));
			context.getExternalContext().getSessionMap().put("idSimulado", this.simulado.getId());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public DataModelResultadoSimulado getDataModelResultadoSimulado() {
		return this.dataModelResultadoSimulado;
	}

	public void setDataModelResultadoSimulado(DataModelResultadoSimulado dataModelResultadoSimulado) {
		this.dataModelResultadoSimulado = dataModelResultadoSimulado;
	}

	public List<RespostaSimulado> getListaRespostaSimulado() {
		return this.listaRespostaSimulado;
	}

	public void setListaRespostaSimulado(List<RespostaSimulado> listaRespostaSimulado) {
		this.listaRespostaSimulado = listaRespostaSimulado;
	}

	public List<ResultadoSimulado> getListaResultadoSimulado() {
		return this.listaResultadoSimulado;
	}

	public void setListaResultadoSimulado(List<ResultadoSimulado> listaResultadoSimulado) {
		this.listaResultadoSimulado = listaResultadoSimulado;
	}

	public Simulado getSimulado() {
		return this.simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}

	public ResultadoSimulado getResultadoSimulado() {
		return this.resultadoSimulado;
	}

	public void setResultadoSimulado(ResultadoSimulado resultadoSimulado) {
		this.resultadoSimulado = resultadoSimulado;
	}

}
