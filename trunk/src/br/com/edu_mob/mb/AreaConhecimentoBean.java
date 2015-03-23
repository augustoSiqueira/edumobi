package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class AreaConhecimentoBean extends GenericBean implements Serializable{

	private static final long serialVersionUID = 3627978460790867150L;
	
	private static final Logger logger = Logger.getLogger(AreaConhecimentoBean.class.getName());
	
	@ManagedProperty(value = "#{dataModelAreaConhecimento}")
	private DataModelAreaConhecimento dataModelAreaConhecimento;
	
	private AreaConhecimentoController areaConhecimentoController;
	
	private CategoriaController categoriaController;

	private List<AreaConhecimento> listaAreaConhecimento = null;

	private List<Categoria> listaCategorias = null;

	private AreaConhecimento areaConhecimento = new AreaConhecimento();

	
	@PostConstruct
	public void init() {
		Filter filtroAreaConhecimento = new Filter();
		filtroAreaConhecimento.put("ativo", Boolean.TRUE);
		filtroAreaConhecimento.put("curso", Boolean.FALSE);
		
		Filter filtroCategoria = new Filter();
		filtroCategoria.put("curso", true);
		
		this.areaConhecimento = new AreaConhecimento(new Categoria());
		this.areaConhecimentoController = (AreaConhecimentoController) this.getBean("areaConhecimentoController", AreaConhecimentoController.class);
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		this.dataModelAreaConhecimento = new DataModelAreaConhecimento();
		try {
			this.listaAreaConhecimento = this.areaConhecimentoController.pesquisarPorFiltro(filtroAreaConhecimento);
			listaCategorias = this.categoriaController.pesquisarPorFiltro(filtroCategoria);
			
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void salvar() {
		if ((this.areaConhecimento != null) && (this.areaConhecimento.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}
	
	public void limparCampos() {
		this.areaConhecimento = new AreaConhecimento();
	}
	
	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaAreaConhecimento = this.areaConhecimentoController.pesquisarPorFiltro(new Filter());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void incluir() {
		try {
			this.areaConhecimentoController.incluir(this.areaConhecimento);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void atualizar() {
		try {
			this.areaConhecimentoController.alterar(this.areaConhecimento);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(ErrorMessage.ERRO.getChave(), e.getListaMensagens());
		}
	}
	
	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if ((this.areaConhecimento != null) && (this.areaConhecimento.getId() != null)) {
				this.areaConhecimentoController.excluir(this.areaConhecimento);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
			}
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), msg));
			}
		}
	}

	public DataModelAreaConhecimento getDataModelAreaConhecimento() {
		return dataModelAreaConhecimento;
	}

	public void setDataModelAreaConhecimento(
			DataModelAreaConhecimento dataModelAreaConhecimento) {
		this.dataModelAreaConhecimento = dataModelAreaConhecimento;
	}

	public List<AreaConhecimento> getListaAreaConhecimento() {
		return listaAreaConhecimento;
	}

	public void setListaAreaConhecimento(
			List<AreaConhecimento> listaAreaConhecimento) {
		this.listaAreaConhecimento = listaAreaConhecimento;
	}
	
	public AreaConhecimento getAreaConhecimento() {
		return areaConhecimento;
	}
	
	public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
		this.areaConhecimento = areaConhecimento;
		if((this.listaAreaConhecimento != null) && !this.listaAreaConhecimento.isEmpty()) {
			this.listaAreaConhecimento.remove(areaConhecimento);
		}
	}

	public CategoriaController getCategoriaController() {
		return categoriaController;
	}

	public void setCategoriaController(CategoriaController categoriaController) {
		this.categoriaController = categoriaController;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
	
	
	
	
}