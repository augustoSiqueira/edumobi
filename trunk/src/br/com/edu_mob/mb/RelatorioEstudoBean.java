package br.com.edu_mob.mb;

import java.io.Serializable;
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
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.RespostaEstudoController;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class RelatorioEstudoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RelatorioEstudoBean.class.getName());

	private CategoriaController categoriaController;

	private RespostaEstudoController respostaEstudoController;

	private Categoria categoria;

	private HorizontalBarChartModel horizontalBarChartModel;

	private Usuario usuarioLogado;

	private static final String ID_CATEGORIA = "1";

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		this.respostaEstudoController = (RespostaEstudoController) this.getBean("respostaEstudoController", RespostaEstudoController.class);
		this.usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			this.categoria = this.categoriaController.pesquisarPorId(Long.parseLong(context.getExternalContext().getRequestParameterMap().get("idCategoria").toString()));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
		this.createHorizontalBarModel();
	}

	public void createHorizontalBarModel() {
		Filter filtro = new Filter();
		this.horizontalBarChartModel = this.initBarModel();

		this.horizontalBarChartModel.setTitle("Resultado Geral");
		this.horizontalBarChartModel.setLegendPosition("ne");
		this.horizontalBarChartModel.setSeriesColors("130873, FF0000");
		this.horizontalBarChartModel.setStacked(true);
		Axis xAxis = this.horizontalBarChartModel.getAxis(AxisType.X);
		xAxis.setLabel("Respostas");

		Axis yAxis = this.horizontalBarChartModel.getAxis(AxisType.Y);
		yAxis.setLabel("Áreas de Conhecimento");

		filtro.put("idUsuario", this.usuarioLogado.getId().toString());
		filtro.put("idCategoria", ID_CATEGORIA);
		yAxis.setMin(0);
		try {
			yAxis.setMax(this.respostaEstudoController.pesquisarPorFiltroCountHQLRelatorio(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	private HorizontalBarChartModel initBarModel() {
		Filter filtro = new Filter();
		HorizontalBarChartModel model = new HorizontalBarChartModel();

		filtro.put("idUsuario", this.usuarioLogado.getId().toString());
		filtro.put("idCategoria", ID_CATEGORIA);

		ChartSeries areaConhecimentoCorretas = new ChartSeries();

		for (AreaConhecimento areaConhecimento : this.categoria.getListaAreasConhecimento()) {
			areaConhecimentoCorretas.setLabel("ACERTOS");
			filtro.put("idAreaConhecimento", areaConhecimento.getId().toString());
			filtro.put("correta", Boolean.TRUE);
			try {
				areaConhecimentoCorretas.set(areaConhecimento.getDescricao().trim().toUpperCase(), this.respostaEstudoController.pesquisarPorFiltroCountHQLRelatorio(filtro));
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			}
		}
		model.addSeries(areaConhecimentoCorretas);

		ChartSeries areaConhecimentoIncorretas = new ChartSeries();

		for (AreaConhecimento areaConhecimento : this.categoria.getListaAreasConhecimento()) {
			areaConhecimentoIncorretas.setLabel("ERROS");
			filtro.put("idAreaConhecimento", areaConhecimento.getId().toString());
			filtro.put("correta", Boolean.FALSE);
			try {
				areaConhecimentoIncorretas.set(areaConhecimento.getDescricao().trim().toUpperCase(), this.respostaEstudoController.pesquisarPorFiltroCountHQLRelatorio(filtro));
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			}
		}
		model.addSeries(areaConhecimentoIncorretas);


		return model;
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", "Item index:" +
				event.getItemIndex() + "Series index: " + event.getSeriesIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public HorizontalBarChartModel getHorizontalBarChartModel() {
		return this.horizontalBarChartModel;
	}

	public void setHorizontalBarChartModel(HorizontalBarChartModel horizontalBarChartModel) {
		this.horizontalBarChartModel = horizontalBarChartModel;
	}

}
