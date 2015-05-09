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
import br.com.edu_mob.util.AliasNavigation;
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

	private boolean habilitarCategoria;
	@PostConstruct
	public void init() {

		this.simulado = new Simulado();
		this.categoriaSelecionada = new Categoria();
		this.dataModelSimuladoDescricao = new DataModelSimuladoDescricao();
		this.listaCategorias = new ArrayList<Categoria>();

		this.simuladoController = (SimuladoDescricaoController) this.getBean(
				"simuladoDescricaoController",
				SimuladoDescricaoController.class);
		this.categoriaController = (CategoriaController) this.getBean(
				"categoriaController", CategoriaController.class);
		this.areaConhecimentoController = (AreaConhecimentoController) this.getBean(
				"areaConhecimentoController", AreaConhecimentoController.class);

		this.areaConhecimentoSource = new ArrayList<AreaConhecimento>();
		this.areaConhecimentoTarget = new ArrayList<AreaConhecimento>();
		this.dualListAreaConhecimento = new DualListModel<AreaConhecimento>(this.areaConhecimentoSource, this.areaConhecimentoTarget);

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
			this.simulado.setAreasConhecimento(this.dualListAreaConhecimento.getTarget());
			this.simulado.setCategoria(this.categoriaSelecionada);
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
			this.simulado.setAreasConhecimento(this.dualListAreaConhecimento.getTarget());
			this.simulado.setCategoria(this.categoriaSelecionada);
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
		this.simulado = new Simulado();
	}

	public void excluir() {
		try {
			this.simuladoController.excluir(this.getSimulado());
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
		this.limparCampos();
		this.carregarCategorias();

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
		this.carregarCategorias();
		this.dualListAreaConhecimento.setTarget(this.simulado.getAreasConhecimento());
		this.carregarAreaConhecimento();
	}


	public void carregarAreaConhecimento() {

		if(this.categoriaSelecionada != null){

			try {
				Filter filtro = new Filter();
				filtro.put("idCategoria", this.categoriaSelecionada.getId().toString());
				this.areaConhecimentoSource = this.areaConhecimentoController.pesquisarPorFiltro(filtro);

				if((this.simulado.getId() != null) && (this.simulado.getAreasConhecimento() != null)){
					this.areaConhecimentoSource.removeAll(this.simulado.getAreasConhecimento());
					this.dualListAreaConhecimento.setSource(this.areaConhecimentoSource);
					if(this.dualListAreaConhecimento.getTarget().size() == 0) {
						this.dualListAreaConhecimento.setTarget(new ArrayList<AreaConhecimento>());
					}
				}else{
					this.dualListAreaConhecimento.setSource(this.areaConhecimentoSource);
					this.dualListAreaConhecimento.setTarget(new ArrayList<AreaConhecimento>());
				}

			} catch (RNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public boolean isHabilitarCategoria() {
		if(this.dualListAreaConhecimento.getTarget().size() == 0) {
			return false;
		}
		return true;
	}

	public String irParaPaginaRespostaSimulado() {
		return AliasNavigation.PAGINA_RESPOSTA_SIMULADO;
	}

	public String irParaPaginaResultadosSimulado() {
		return AliasNavigation.PAGINA_RESULTADOS_SIMULADO;
	}

	public DataModelSimuladoDescricao getDataModelSimuladoDescricao() {
		return this.dataModelSimuladoDescricao;
	}

	public void setDataModelSimuladoDescricao(
			DataModelSimuladoDescricao dataModelSimuladoDescricao) {
		this.dataModelSimuladoDescricao = dataModelSimuladoDescricao;
	}

	public SimuladoDescricaoController getSimuladoController() {
		return this.simuladoController;
	}

	public void setSimuladoController(
			SimuladoDescricaoController simuladoController) {
		this.simuladoController = simuladoController;
	}

	public Simulado getSimulado() {
		return this.simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}

	public List<Categoria> getListaCategorias() {
		return this.listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public CategoriaController getCategoriaController() {
		return this.categoriaController;
	}

	public void setCategoriaController(CategoriaController categoriaController) {
		this.categoriaController = categoriaController;
	}

	public Categoria getCategoriaSelecionada() {
		return this.categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public AreaConhecimentoController getAreaConhecimentoController() {
		return this.areaConhecimentoController;
	}

	public void setAreaConhecimentoController(
			AreaConhecimentoController areaConhecimentoController) {
		this.areaConhecimentoController = areaConhecimentoController;
	}

	public DualListModel<AreaConhecimento> getDualListAreaConhecimento() {
		return this.dualListAreaConhecimento;
	}

	public void setDualListAreaConhecimento(
			DualListModel<AreaConhecimento> dualListAreaConhecimento) {
		this.dualListAreaConhecimento = dualListAreaConhecimento;
	}

	public List<AreaConhecimento> getAreaConhecimentoSource() {
		return this.areaConhecimentoSource;
	}

	public void setAreaConhecimentoSource(
			List<AreaConhecimento> areaConhecimentoSource) {
		this.areaConhecimentoSource = areaConhecimentoSource;
	}

	public List<AreaConhecimento> getAreaConhecimentoTarget() {
		return this.areaConhecimentoTarget;
	}

	public void setAreaConhecimentoTarget(
			List<AreaConhecimento> areaConhecimentoTarget) {
		this.areaConhecimentoTarget = areaConhecimentoTarget;
	}


}
