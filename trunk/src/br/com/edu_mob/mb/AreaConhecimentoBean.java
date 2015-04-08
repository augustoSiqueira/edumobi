package br.com.edu_mob.mb;

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
	
	private List<AreaConhecimento> lista = new ArrayList<AreaConhecimento>();
	
	@PostConstruct
	public void init() {
		
		
		// Redireciona o usuário pra tela de categoria, se ele não tiver a sessão de categoria
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
		boolean existe = false;
		
		for (int i = 0; i < this.listaAreaConhecimento.size(); i++) {
			if(this.areaConhecimento.getDescricao().trim().equalsIgnoreCase(this.listaAreaConhecimento.get(i).getDescricao().trim())){
				if(this.areaConhecimento.getId() != this.listaAreaConhecimento.get(i).getId()){
					this.listaAreaConhecimento.remove(this.areaConhecimentoSelecionado);
					existe = true;
					break;
				}
			}
		}
		
		if(existe){
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave()),
				ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave(), Entidades.AREA_CONHECIMENTO.getValor());
		}else{
			if (this.areaConhecimentoSelecionado.getDescricao() != null) {
				if ((this.areaConhecimentoSelecionado.getId() == this.areaConhecimento.getId())
						&& (this.areaConhecimentoSelecionado.getDescricao().equalsIgnoreCase(this.areaConhecimento.getDescricao()))) {
					this.areaConhecimento.setCategoria(categoria);
					this.areaConhecimento.setDescricao(this.areaConhecimento.getDescricao().trim());
					this.listaAreaConhecimento.remove(this.areaConhecimentoSelecionado);
					this.listaAreaConhecimento.add(this.areaConhecimento);
					this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO
							.getValor()), SucessMessage.ATUALIZADA_SUCESSO
							.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
					limparCampos();
					this.atualizou = false;
				}
			}
		}
	}

	/*
	 * Realiza o cadastro de area de conhecimento
	 * */
	private void cadastrarAreaConhecimento() {
		boolean existe = false;
		
		for (int i = 0; i < this.listaAreaConhecimento.size(); i++) {
			if(this.areaConhecimento.getDescricao().trim().equalsIgnoreCase(this.listaAreaConhecimento.get(i).getDescricao().trim())){
				existe = true;
				break;
			}
		}
		
		if(existe){
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave()),
				ErrorMessage.AREA_CONHECIMENTO_DESC_EXISTENTE.getChave(), Entidades.AREA_CONHECIMENTO.getValor());
		}else{
			this.areaConhecimento.setCategoria(categoria);
			this.areaConhecimento.setDescricao(this.areaConhecimento.getDescricao().trim());
			this.listaAreaConhecimento.add(this.areaConhecimento);
			this.areaConhecimento= new AreaConhecimento();
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADA_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
			limparCampos();
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
	
	public void incluirLista() {
		try {
			List<AreaConhecimento> lista = new ArrayList<AreaConhecimento>();
			
			
			if (this.areaConhecimento.getDescricao() != null
					&& !this.areaConhecimento.getDescricao().isEmpty()) {
				if (this.listaAreaConhecimento != null
						&& this.listaAreaConhecimento.size() > 1) {
					for (AreaConhecimento areaConhecimento : listaAreaConhecimento) {
						areaConhecimento.setCategoria(this.categoria);
						lista.add(areaConhecimento);
						this.listaAreaConhecimento = lista;
					}
				} else {
					areaConhecimento.setCategoria(this.categoria);
					lista.add(areaConhecimento);
					this.listaAreaConhecimento = lista;
				}
			}
			
			this.areaConhecimentoController.incluirLista(this.listaAreaConhecimento);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.AREA_CONHECIMENTO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
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

	public List<AreaConhecimento> getLista() {
		return lista;
	}

	public void setLista(List<AreaConhecimento> lista) {
		this.lista = lista;
	}
}