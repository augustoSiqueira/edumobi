package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.SimuladoDescricaoController;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Simulado;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class SimuladoDescricaoBean extends GenericBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(SimuladoDescricaoBean.class.getName());

	@ManagedProperty(value = "#{dataModelSimuladoDescricao}")
	private DataModelSimuladoDescricao dataModelSimuladoDescricao;

	private SimuladoDescricaoController simuladoController;
	private CategoriaController categoriaController;
	private AreaConhecimentoController areaConhecimentoController;
	private Simulado simulado;

	private List<Categoria> listaCategorias;
	private Categoria categoriaSelecionada;

	private DualListModel<AreaConhecimento> dualListAreaConhecimento;
	
	private List<AreaConhecimento> areaConhecimentoSource;
	private List<AreaConhecimento> areaConhecimentoTarget;
	
	@PostConstruct
	public void init() {

		simulado = new Simulado();
		categoriaSelecionada = new Categoria();
		dataModelSimuladoDescricao = new DataModelSimuladoDescricao();
		listaCategorias = new ArrayList<Categoria>();

		simuladoController = (SimuladoDescricaoController) this.getBean(
				"simuladoDescricaoController",
				SimuladoDescricaoController.class);
		categoriaController = (CategoriaController) this.getBean(
				"categoriaController", CategoriaController.class);
		areaConhecimentoController = (AreaConhecimentoController) this.getBean(
				"areaConhecimentoController", AreaConhecimentoController.class);
		
		areaConhecimentoSource = new ArrayList<AreaConhecimento>();
		areaConhecimentoTarget = new ArrayList<AreaConhecimento>();
		dualListAreaConhecimento = new DualListModel<AreaConhecimento>(areaConhecimentoSource, areaConhecimentoTarget);
		
	}

	public void salvar() {
		if ((this.simulado != null) && (this.simulado.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}

	public void incluir() {
		try {
			this.simulado.setAreasConhecimento(dualListAreaConhecimento.getTarget());
			this.simulado.setCategoria(categoriaSelecionada);
			this.simuladoController.incluir(this.simulado);
			this.addMessage(
					MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(),
					Entidades.SIMULADO.getValor());
			this.atualizarGrid();

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').hide();");
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void atualizar() {
		try {			
			this.simulado.setAreasConhecimento(dualListAreaConhecimento.getTarget());
			this.simulado.setCategoria(categoriaSelecionada);
			this.simuladoController.alterar(this.simulado);
			this.addMessage(
					MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(),
					Entidades.SIMULADO.getValor());
			this.atualizarGrid();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').hide();");
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(ErrorMessage.ERRO.getChave(), e.getListaMensagens());
		}
	}

	public void limparCampos() {
		simulado = new Simulado();
	}

	public void excluir() {
		try {
			simuladoController.excluir(getSimulado());
			this.addMessage(
					MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.EXCLUIDO_SUCESSO.getValor(),
					Entidades.SIMULADO.getValor());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	
	
	public void atualizarGrid() {
		limparCampos();
		carregarCategorias();
			
	}
	public void carregarCategorias(){
		try {
			Filter filtroCategoria = new Filter();
			filtroCategoria.put("ativo", Boolean.TRUE);
			filtroCategoria.put("curso", Boolean.TRUE);
			this.listaCategorias = this.categoriaController
					.pesquisarPorFiltro(filtroCategoria);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
		
	}
	
	public void prepararEdicao(){
		carregarCategorias();
		carregarAreaConhecimento();
	}

	public void carregarAreaConhecimento() {
		
		if(categoriaSelecionada != null){
			
			try {
				Filter filtro = new Filter();
				filtro.put("idCategoria", categoriaSelecionada.getId().toString());
				areaConhecimentoSource = areaConhecimentoController.pesquisarPorFiltro(filtro);
				
				if(simulado.getId() != null && simulado.getAreasConhecimento() != null){
					areaConhecimentoSource.removeAll(simulado.getAreasConhecimento());
					dualListAreaConhecimento.setSource(areaConhecimentoSource);
					dualListAreaConhecimento.setTarget(simulado.getAreasConhecimento());
				}else{
					dualListAreaConhecimento.setSource(areaConhecimentoSource);
					dualListAreaConhecimento.setTarget(new ArrayList<AreaConhecimento>());
				}				
				
			} catch (RNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public DataModelSimuladoDescricao getDataModelSimuladoDescricao() {
		return dataModelSimuladoDescricao;
	}

	public void setDataModelSimuladoDescricao(
			DataModelSimuladoDescricao dataModelSimuladoDescricao) {
		this.dataModelSimuladoDescricao = dataModelSimuladoDescricao;
	}

	public SimuladoDescricaoController getSimuladoController() {
		return simuladoController;
	}

	public void setSimuladoController(
			SimuladoDescricaoController simuladoController) {
		this.simuladoController = simuladoController;
	}

	public Simulado getSimulado() {
		return simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public CategoriaController getCategoriaController() {
		return categoriaController;
	}

	public void setCategoriaController(CategoriaController categoriaController) {
		this.categoriaController = categoriaController;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public AreaConhecimentoController getAreaConhecimentoController() {
		return areaConhecimentoController;
	}

	public void setAreaConhecimentoController(
			AreaConhecimentoController areaConhecimentoController) {
		this.areaConhecimentoController = areaConhecimentoController;
	}

	public DualListModel<AreaConhecimento> getDualListAreaConhecimento() {
		return dualListAreaConhecimento;
	}

	public void setDualListAreaConhecimento(
			DualListModel<AreaConhecimento> dualListAreaConhecimento) {
		this.dualListAreaConhecimento = dualListAreaConhecimento;
	}

	public List<AreaConhecimento> getAreaConhecimentoSource() {
		return areaConhecimentoSource;
	}

	public void setAreaConhecimentoSource(
			List<AreaConhecimento> areaConhecimentoSource) {
		this.areaConhecimentoSource = areaConhecimentoSource;
	}

	public List<AreaConhecimento> getAreaConhecimentoTarget() {
		return areaConhecimentoTarget;
	}

	public void setAreaConhecimentoTarget(
			List<AreaConhecimento> areaConhecimentoTarget) {
		this.areaConhecimentoTarget = areaConhecimentoTarget;
	}
	
	
}
