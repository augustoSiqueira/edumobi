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

import org.primefaces.model.DualListModel;

import br.com.edu_mob.controller.FuncionalidadeController;
import br.com.edu_mob.controller.PerfilController;
import br.com.edu_mob.entity.Funcionalidade;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class PerfilBean extends GenericBean implements Serializable {

	private static final Logger logger = Logger.getLogger(PerfilBean.class.getName());

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{dataModelPerfil}")
	private DataModelPerfil dataModelPerfil;

	private PerfilController perfilController;

	private FuncionalidadeController funcionalidadeController;

	private List<Perfil> listaPerfis = null;

	private List<Funcionalidade> listaFuncionalidadeTarget;

	private List<Funcionalidade> listaFuncionalidadeSource;

	private DualListModel<Funcionalidade> listaFuncionalidade;

	private Perfil perfil = new Perfil();

	@PostConstruct
	public void init() {
		this.perfil = new Perfil();
		this.perfilController = (PerfilController) this.getBean("perfilController", PerfilController.class);
		this.dataModelPerfil = new DataModelPerfil();
		this.funcionalidadeController =
				(FuncionalidadeController) this.getBean("funcionalidadeController", FuncionalidadeController.class);
		try {
			this.listaFuncionalidadeSource = this.funcionalidadeController.pesquisarPorFiltro(new Filter());
			this.listaFuncionalidadeTarget = new ArrayList<Funcionalidade>();
			this.listaFuncionalidade =
					new DualListModel<Funcionalidade>(this.listaFuncionalidadeSource, this.listaFuncionalidadeTarget);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void carregarFuncionalidades() {
		try {
			List<Funcionalidade> listaFuncionalidades = this.funcionalidadeController.pesquisarPorFiltro(new Filter());
			List<Funcionalidade> listaFuncionalidadesNaoAssociadas = new ArrayList<Funcionalidade>();
			if ((this.perfil != null) && (this.perfil.getListaFuncionalidades() != null)) {
				for (Funcionalidade funcionalidade : listaFuncionalidades) {
					if (!this.perfil.getListaFuncionalidades().contains(funcionalidade)) {
						listaFuncionalidadesNaoAssociadas.add(funcionalidade);
					}
				}
				this.listaFuncionalidade =
						new DualListModel<Funcionalidade>(listaFuncionalidadesNaoAssociadas, this.perfil.getListaFuncionalidades());
			} else {
				this.listaFuncionalidade =
						new DualListModel<Funcionalidade>(listaFuncionalidades, new ArrayList<Funcionalidade>());
			}

		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void salvar() {
		if ((this.perfil != null) && (this.perfil.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}

	public void limparCampos() {
		this.perfil = new Perfil();
		try {
			this.listaFuncionalidade =
					new DualListModel<Funcionalidade>(this.funcionalidadeController.pesquisarPorFiltro(new Filter()),
							new ArrayList<Funcionalidade>());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaPerfis = this.perfilController.pesquisarPorFiltro(new Filter());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void incluir() {
		try {
			this.perfil.setListaFuncionalidades(this.listaFuncionalidade.getTarget());
			this.perfilController.incluir(this.perfil);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.PERFIL.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizar() {
		try {
			this.perfil.setListaFuncionalidades(this.listaFuncionalidade.getTarget());
			this.perfilController.alterar(this.perfil);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.PERFIL.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(ErrorMessage.ERRO.getChave(), e.getListaMensagens());
		}
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if ((this.perfil != null) && (this.perfil.getId() != null)) {
				this.perfilController.excluir(this.perfil);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.PERFIL.getValor());
			}
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), msg));
			}
		}
	}

	public List<Perfil> getListaPerfis() {
		return this.listaPerfis;
	}

	public void setListaPerfis(List<Perfil> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	public DataModelPerfil getDataModelPerfil() {
		return this.dataModelPerfil;
	}

	public void setDataModelPerfil(DataModelPerfil dataModelPerfil) {
		this.dataModelPerfil = dataModelPerfil;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
		if ((perfil != null) && (perfil.getId() != null)) {
			this.carregarFuncionalidades();
		}
	}

	public List<Funcionalidade> getListaFuncionalidadeTarget() {
		return this.listaFuncionalidadeTarget;
	}

	public void setListaFuncionalidadeTarget(List<Funcionalidade> listaFuncionalidadeTarget) {
		this.listaFuncionalidadeTarget = listaFuncionalidadeTarget;
	}

	public List<Funcionalidade> getListaFuncionalidadeSource() {
		return this.listaFuncionalidadeSource;
	}

	public void setListaFuncionalidadeSource(List<Funcionalidade> listaFuncionalidadeSource) {
		this.listaFuncionalidadeSource = listaFuncionalidadeSource;
	}

	public DualListModel<Funcionalidade> getListaFuncionalidade() {
		return this.listaFuncionalidade;
	}

	public void setListaFuncionalidade(DualListModel<Funcionalidade> listaFuncionalidade) {
		this.listaFuncionalidade = listaFuncionalidade;
	}

}
