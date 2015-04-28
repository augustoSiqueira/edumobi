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
import org.primefaces.model.chart.PieChartModel;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.controller.RespostaEstudoController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class CursoBean extends GenericBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CursoBean.class.getName());

	private CategoriaController categoriaController;

	private RespostaEstudoController respostaEstudoController;

	private QuestaoController questaoController;

	private PieChartModel pieChartModel;

	private Categoria categoria;

	private int totalQuestoesCategoria = 0;

	private int qtdRespondidas = 0;

	private int qtdCorretas = 0;

	private int qtdErradas = 0;

	@PostConstruct
	public void init() {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FacesContext context = FacesContext.getCurrentInstance();
		Filter filtroQuestao = new Filter();
		Filter filtroResposta = new Filter();
		this.categoria = new Categoria();
		this.pieChartModel = new PieChartModel();
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		this.questaoController = (QuestaoController) this.getBean("questaoController", QuestaoController.class);
		this.respostaEstudoController = (RespostaEstudoController) this.getBean("respostaEstudoController", RespostaEstudoController.class);
		try {
			if(context.getExternalContext().getSessionMap().get("cursoId") != null) {
				String idCategoria = String.valueOf(context.getExternalContext().getSessionMap().get("cursoId"));
				this.categoria = this.categoriaController.pesquisarPorId(Long.parseLong(idCategoria));
				filtroQuestao.put("idCategoria", this.categoria.getId().toString());
				this.totalQuestoesCategoria = this.questaoController.pesquisarQtdTotalQuestoes(filtroQuestao);
				filtroResposta.put("idUsuario", usuario.getId().toString());
				filtroResposta.put("idCategoria", this.categoria.getId().toString());
				this.qtdRespondidas = this.respostaEstudoController.pesquisarPorFiltroCountHQLRelatorio(filtroResposta);
				filtroResposta.put("correta", Boolean.TRUE);
				this.qtdCorretas = this.respostaEstudoController.pesquisarPorFiltroCountHQLRelatorio(filtroResposta);
				filtroResposta.put("correta", Boolean.FALSE);
				this.qtdErradas = this.respostaEstudoController.pesquisarPorFiltroCountHQLRelatorio(filtroResposta);
				this.createPieModel();
				context.getExternalContext().getSessionMap().remove("cursoId");
			}
		}catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void createPieModel() {
		this.pieChartModel.setTitle("An�lise Estudo");
		this.pieChartModel.setLegendPosition("e");
		this.pieChartModel.set("ACERTOS", this.qtdCorretas);
		this.pieChartModel.set("ERROS", this.qtdErradas);
		this.pieChartModel.setSeriesColors("130873, FF0000");
		this.pieChartModel.setFill(false);
		this.pieChartModel.setShowDataLabels(true);
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public PieChartModel getPieChartModel() {
		return this.pieChartModel;
	}

	public void setPieChartModel(PieChartModel pieChartModel) {
		this.pieChartModel = pieChartModel;
	}

	public int getTotalQuestoesCategoria() {
		return this.totalQuestoesCategoria;
	}

	public void setTotalQuestoesCategoria(int totalQuestoesCategoria) {
		this.totalQuestoesCategoria = totalQuestoesCategoria;
	}

	public int getQtdCorretas() {
		return this.qtdCorretas;
	}

	public void setQtdCorretas(int qtdCorretas) {
		this.qtdCorretas = qtdCorretas;
	}

	public int getQtdErradas() {
		return this.qtdErradas;
	}

	public void setQtdErradas(int qtdErradas) {
		this.qtdErradas = qtdErradas;
	}

	public int getQtdRespondidas() {
		return this.qtdRespondidas;
	}

	public void setQtdRespondidas(int qtdRespondidas) {
		this.qtdRespondidas = qtdRespondidas;
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", "Item index:" +
				event.getItemIndex() + "Series index: " + event.getSeriesIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}