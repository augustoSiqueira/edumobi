package br.com.edu_mob.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import br.com.edu_mob.util.UtilSession;

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

	private Categoria categoria;
	
	Filter filtroAreaConhecimento = new Filter();
	
	private boolean atualizou = false;
	
	private AreaConhecimento areaConhecimentoSelecionado = new AreaConhecimento();
	
	@PostConstruct
	public void init() {
		
		
		// Redireciona o usu�rio pra tela de categoria, se ele n�o tiver a sess�o de categoria
		try {
			this.categoria = (Categoria) UtilSession.getHttpSessionObject("categoriaSelecionada");
			
			if(this.categoria != null){
				
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("categoria.jsf");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.filtroAreaConhecimento.put("idCategoria", this.categoria.getId().toString());
		
		this.areaConhecimento = new AreaConhecimento();
		this.areaConhecimentoController = (AreaConhecimentoController) this.getBean("areaConhecimentoController", AreaConhecimentoController.class);
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		this.dataModelAreaConhecimento = new DataModelAreaConhecimento();
		this.areaConhecimentoSelecionado = new AreaConhecimento();
		
		try {
			this.listaAreaConhecimento = this.areaConhecimentoController.pesquisarPorFiltro(this.filtroAreaConhecimento);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void btnAlterar(){
		this.atualizou = true;
	}
	
	public void adicionarAreaConhecimento() {
		if(atualizou){
			alterarAreaConhecimento();
		}else{
			cadastrarAreaConhecimento();
		}
	}
	
	private void alterarAreaConhecimento() {
		
		try {
			this.areaConhecimento.setCategoria(this.categoria);
			this.areaConhecimento.setDescricao(this.areaConhecimento.getDescricao().trim());
			//boolean existe = this.areaConhecimentoController.verificarExistencia(this.areaConhecimento);
			boolean existe = validarSeExisteLocal(this.areaConhecimento);
			if(existe){
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave()),
				ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave(), Entidades.AREA_CONHECIMENTO.getValor());
			}else{
				this.areaConhecimentoController.alterar(this.areaConhecimento);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
				SucessMessage.ATUALIZADA_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
				atualizarGrid();
			}
			
		} catch (RNException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Realiza o cadastro de area de conhecimento
	 * */
	private void cadastrarAreaConhecimento() {

		try {
			this.areaConhecimento.setCategoria(this.categoria);
			this.areaConhecimento.setDescricao(this.areaConhecimento.getDescricao().trim());
			//boolean existe = this.areaConhecimentoController.verificarExistencia(this.areaConhecimento);
			boolean existe = validarSeExisteLocal(this.areaConhecimento);
						
			if(existe){
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave()),
				ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave(), Entidades.AREA_CONHECIMENTO.getValor());
			}else{
				this.areaConhecimentoController.incluir(this.areaConhecimento);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
				SucessMessage.CADASTRADA_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
				atualizarGrid();
			}
			
		} catch (RNException e) {
			e.printStackTrace();
		}
	}

	public void limparCampos() {
		if(this.areaConhecimento != null){
			this.areaConhecimento = new AreaConhecimento();
			this.areaConhecimentoSelecionado = new AreaConhecimento();
		}
	}
	
	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaAreaConhecimento = this.areaConhecimentoController.pesquisarPorFiltro(this.filtroAreaConhecimento);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void salvar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("categoria.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if ((this.areaConhecimento != null) && (this.areaConhecimento.getId() != null)) {
				this.areaConhecimentoController.excluir(this.areaConhecimento);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
				limparCampos();
			}else{
				this.listaAreaConhecimento.remove(this.areaConhecimento);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
				limparCampos();
			}
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), msg));
			}
		}
	}
	
	private boolean validarSeExisteLocal(AreaConhecimento area){
		
		for (AreaConhecimento ac : dataModelAreaConhecimento) {
			if(area.getId() == null){
				if(area.getDescricao().equals(ac.getDescricao())){
					return true;
				}
			}else{
				if(area.getDescricao().equals(ac.getDescricao()) && area.getId()!= ac.getId()){
					return true;
				}
			}
		}
		return false;
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
		this.areaConhecimentoSelecionado = areaConhecimento;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Filter getFiltroAreaConhecimento() {
		return filtroAreaConhecimento;
	}

	public void setFiltroAreaConhecimento(Filter filtroAreaConhecimento) {
		this.filtroAreaConhecimento = filtroAreaConhecimento;
	}

	public boolean isAtualizou() {
		return atualizou;
	}

	public void setAtualizou(boolean atualizou) {
		this.atualizou = atualizou;
	}

	public AreaConhecimento getAreaConhecimentoSelecionado() {
		return areaConhecimentoSelecionado;
	}

	public void setAreaConhecimentoSelecionado(
			AreaConhecimento areaConhecimentoSelecionado) {
		this.areaConhecimentoSelecionado = areaConhecimentoSelecionado;
	}
}