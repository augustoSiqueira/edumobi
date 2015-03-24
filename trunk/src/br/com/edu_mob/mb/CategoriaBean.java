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

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class CategoriaBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CategoriaBean.class.getName());

	@ManagedProperty(value = "#{dataModelCategoria}")
	private DataModelCategoria dataModelCategoria;

	private CategoriaController categoriaController;

	private List<Categoria> listaCategorias = null;

	private Categoria categoria = new Categoria();

	@PostConstruct
	public void init() {
		Filter filtroCategoria = new Filter();
		filtroCategoria.put("ativo", Boolean.TRUE);
		filtroCategoria.put("curso", Boolean.FALSE);
		this.categoria = new Categoria();
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		this.dataModelCategoria = new DataModelCategoria();
		try {
			this.listaCategorias = this.categoriaController.pesquisarPorFiltro(filtroCategoria);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void salvar() {
		if ((this.categoria != null) && (this.categoria.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}

	public void limparCampos() {
		this.categoria = new Categoria();
	}

	public void atualizarGrid() {
		try {
			this.limparCampos();
			Filter filtroCategoria = new Filter();
			filtroCategoria.put("ativo", Boolean.TRUE);
			filtroCategoria.put("curso", Boolean.FALSE);
			this.listaCategorias = this.categoriaController.pesquisarPorFiltro(filtroCategoria);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void incluir() {
		try {
			this.categoriaController.incluir(this.categoria);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.CATEGORIA.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizar() {
		try {
			this.categoriaController.alterar(this.categoria);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.CATEGORIA.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(ErrorMessage.ERRO.getChave(), e.getListaMensagens());
		}
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if ((this.categoria != null) && (this.categoria.getId() != null)) {
				this.categoriaController.excluir(this.categoria);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.CATEGORIA.getValor());
			}
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), msg));
			}
		}
	}

	public DataModelCategoria getDataModelCategoria() {
		return this.dataModelCategoria;
	}

	public void setDataModelCategoria(DataModelCategoria dataModelCategoria) {
		this.dataModelCategoria = dataModelCategoria;
	}

	public List<Categoria> getListaCategorias() {
		return this.listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		if((this.listaCategorias != null) && !this.listaCategorias.isEmpty()) {
			this.listaCategorias.remove(categoria);
		}
	}

}
