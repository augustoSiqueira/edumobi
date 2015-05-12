package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.edu_mob.controller.RespostaSimuladoController;
import br.com.edu_mob.controller.ResultadoSimuladoController;
import br.com.edu_mob.entity.RespostaSimulado;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class GabaritoSimuladoBean extends GenericBean implements Serializable {

	private static final Logger logger = Logger.getLogger(GabaritoSimuladoBean.class.getName());

	private static final long serialVersionUID = 1L;

	private RespostaSimuladoController respostaSimuladoController;

	private ResultadoSimuladoController resultadoSimuladoController;

	private List<RespostaSimulado> listaRespostaSimulado = null;

	private ResultadoSimulado resultadoSimulado;

	private PieChartModel pieChartModel;

	private LineChartModel lineChartModel;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.pieChartModel = new PieChartModel();
		this.lineChartModel = new LineChartModel();
		//Filter filtro = new Filter();
		this.respostaSimuladoController = (RespostaSimuladoController) this.getBean("respostaSimuladoController", RespostaSimuladoController.class);
		this.resultadoSimuladoController = (ResultadoSimuladoController) this.getBean("resultadoSimuladoController", ResultadoSimuladoController.class);
		try {
			this.resultadoSimulado = this.resultadoSimuladoController.pesquisarPorId(Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idResultadoSimulado").toString()));
		}  catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
		this.createPieModel();
	}

	public void createPieModel() {
		this.pieChartModel.setTitle("Análise Estudo");
		this.pieChartModel.setLegendPosition("e");
		this.pieChartModel.set("ACERTOS", this.resultadoSimulado.getQtdAcertos());
		this.pieChartModel.set("ERROS", this.resultadoSimulado.getQtdErros());
		this.pieChartModel.setSeriesColors("130873, FF0000");
		this.pieChartModel.setFill(false);
		this.pieChartModel.setShowDataLabels(true);
	}

	public List<RespostaSimulado> getListaRespostaSimulado() {
		return this.listaRespostaSimulado;
	}

	public void setListaRespostaSimulado(List<RespostaSimulado> listaRespostaSimulado) {
		this.listaRespostaSimulado = listaRespostaSimulado;
	}

	public ResultadoSimulado getResultadoSimulado() {
		return this.resultadoSimulado;
	}

	public void setResultadoSimulado(ResultadoSimulado resultadoSimulado) {
		this.resultadoSimulado = resultadoSimulado;
	}

	public PieChartModel getPieChartModel() {
		return this.pieChartModel;
	}

	public void setPieChartModel(PieChartModel pieChartModel) {
		this.pieChartModel = pieChartModel;
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", "Item index:" +
				event.getItemIndex() + "Series index: " + event.getSeriesIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
