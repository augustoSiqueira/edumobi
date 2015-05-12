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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;

import br.com.edu_mob.controller.ResultadoSimuladoController;
import br.com.edu_mob.controller.SimuladoDescricaoController;
import br.com.edu_mob.entity.ResultadoSimulado;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class RelatorioSimuladoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RelatorioSimuladoBean.class.getName());

	private ResultadoSimuladoController resultadoSimuladoController;

	private SimuladoDescricaoController simuladoDescricaoController;

	private List<ResultadoSimulado> listaResultadosSimulado;

	private LineChartModel lineChartModel;

	private BarChartModel barChartModel;

	private HorizontalBarChartModel horizontalBarChartModel;

	private Simulado simulado;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.lineChartModel = new LineChartModel();
		Filter filtro = new Filter();
		this.resultadoSimuladoController = (ResultadoSimuladoController) this.getBean("resultadoSimuladoController", ResultadoSimuladoController.class);
		this.simuladoDescricaoController = (SimuladoDescricaoController) this.getBean("simuladoDescricaoController", SimuladoDescricaoController.class);
		try {
			this.simulado = this.simuladoDescricaoController.pesquisarPorId(Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idSimulado").toString()));
			filtro.put("idSimulado", this.simulado.getId().toString());
			this.listaResultadosSimulado = this.resultadoSimuladoController.pesquisarPorFiltro(filtro);
		}  catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
		this.createLineModel();
		this.createBarModel();
		this.createHorizontalBarModel();
	}

	public void createLineModel() {
		this.lineChartModel = this.initSimuladoModel();

		this.lineChartModel.setTitle("Simulado Análise Geral (Line)");
		this.lineChartModel.setLegendPosition("e");
		this.lineChartModel.setShowPointLabels(true);
		this.lineChartModel.setSeriesColors("130873, FF0000");
		this.lineChartModel.getAxes().put(AxisType.X, new CategoryAxis("SIMULADOS"));
		Axis yAxis = this.lineChartModel.getAxis(AxisType.Y);
		yAxis.setLabel("TOTAL QUESTÕES");
		yAxis.setMin(0);
		yAxis.setMax(this.simulado.getQntQuestao());
	}

	public void createBarModel() {
		this.barChartModel = this.initBarModel();

		this.barChartModel.setTitle("Simulado Análise Geral (Bar)");
		this.barChartModel.setLegendPosition("ne");
		this.barChartModel.setSeriesColors("130873, FF0000");
		Axis xAxis = this.barChartModel.getAxes().put(AxisType.X, new CategoryAxis("SIMULADOS"));
		xAxis.setLabel("SIMULADOS");

		Axis yAxis = this.barChartModel.getAxis(AxisType.Y);
		yAxis.setLabel("TOTAL QUESTÕES");
		yAxis.setMin(0);
		yAxis.setMax(this.simulado.getQntQuestao());
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries acertos = new ChartSeries();
		acertos.setLabel("ACERTOS");
		ChartSeries erros = new ChartSeries();
		erros.setLabel("ERROS");
		if((this.listaResultadosSimulado != null) && !this.listaResultadosSimulado.isEmpty()) {
			for (int i = 0; i < this.listaResultadosSimulado.size(); i++) {
				acertos.set(i + 1, this.listaResultadosSimulado.get(i).getQtdAcertos());
				erros.set(i + 1, this.listaResultadosSimulado.get(i).getQtdErros());
			}
		}

		model.addSeries(acertos);
		model.addSeries(erros);

		return model;
	}

	private void createHorizontalBarModel() {
		this.horizontalBarChartModel = new HorizontalBarChartModel();

		ChartSeries acertos = new ChartSeries();
		acertos.setLabel("ACERTOS");
		ChartSeries erros = new ChartSeries();
		erros.setLabel("ERROS");
		if((this.listaResultadosSimulado != null) && !this.listaResultadosSimulado.isEmpty()) {
			for (int i = 0; i < this.listaResultadosSimulado.size(); i++) {
				acertos.set(i + 1, this.listaResultadosSimulado.get(i).getQtdAcertos());
				erros.set(i + 1, this.listaResultadosSimulado.get(i).getQtdErros());
			}
		}

		this.horizontalBarChartModel.addSeries(acertos);
		this.horizontalBarChartModel.addSeries(erros);

		this.horizontalBarChartModel.setTitle("Simulado Análise Geral (Horizontal Bar)");
		this.horizontalBarChartModel.setLegendPosition("e");
		this.horizontalBarChartModel.setStacked(true);
		this.horizontalBarChartModel.setSeriesColors("130873, FF0000");

		Axis xAxis = this.horizontalBarChartModel.getAxis(AxisType.X);
		xAxis.setLabel("TOTAL QUESTÕES");
		xAxis.setMin(0);
		xAxis.setMax(this.simulado.getQntQuestao());

		Axis yAxis = this.horizontalBarChartModel.getAxis(AxisType.Y);
		yAxis.setLabel("SIMULADOS");

	}

	private LineChartModel initSimuladoModel() {
		LineChartModel lineChartModel = new LineChartModel();

		ChartSeries acertos = new ChartSeries();
		acertos.setLabel("ACERTOS");
		ChartSeries erros = new ChartSeries();
		erros.setLabel("ERROS");
		if((this.listaResultadosSimulado != null) && !this.listaResultadosSimulado.isEmpty()) {
			for (int i = 0; i < this.listaResultadosSimulado.size(); i++) {
				acertos.set(i + 1, this.listaResultadosSimulado.get(i).getQtdAcertos());
				erros.set(i + 1, this.listaResultadosSimulado.get(i).getQtdErros());
			}
		}

		lineChartModel.addSeries(acertos);
		lineChartModel.addSeries(erros);

		return lineChartModel;
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", "Item index:" +
				event.getItemIndex() + "Series index: " + event.getSeriesIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ResultadoSimulado> getListaResultadosSimulado() {
		return this.listaResultadosSimulado;
	}

	public void setListaResultadosSimulado(List<ResultadoSimulado> listaResultadosSimulado) {
		this.listaResultadosSimulado = listaResultadosSimulado;
	}

	public LineChartModel getLineChartModel() {
		return this.lineChartModel;
	}

	public void setLineChartModel(LineChartModel lineChartModel) {
		this.lineChartModel = lineChartModel;
	}

	public Simulado getSimulado() {
		return this.simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}

	public BarChartModel getBarChartModel() {
		return this.barChartModel;
	}

	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}

	public HorizontalBarChartModel getHorizontalBarChartModel() {
		return this.horizontalBarChartModel;
	}

	public void setHorizontalBarChartModel(HorizontalBarChartModel horizontalBarChartModel) {
		this.horizontalBarChartModel = horizontalBarChartModel;
	}

}
