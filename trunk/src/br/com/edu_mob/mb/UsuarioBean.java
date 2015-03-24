package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.edu_mob.controller.PerfilController;
import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.entity.Perfil;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean extends GenericBean implements Serializable {

	private static final Logger logger = Logger.getLogger(UsuarioBean.class.getName());

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{dataModelUsuario}")
	private DataModelUsuario dataModelUsuario;

	private UsuarioController usuarioController;

	private PerfilController perfilController;

	private List<Usuario> listaUsuarios = null;

	private List<Perfil> listaPerfis = null;

	private Usuario usuario;

	@PostConstruct
	public void init() {
		Filter filtroPerfil = new Filter();
		this.usuario = new Usuario();
		this.usuarioController = (UsuarioController) this.getBean("usuarioController", UsuarioController.class);
		this.perfilController = (PerfilController) this.getBean("perfilController", PerfilController.class);
		this.dataModelUsuario = new DataModelUsuario();
		try {
			this.filtro.put("ativo", Boolean.TRUE);
			this.listaPerfis = this.perfilController.pesquisarPorFiltro(filtroPerfil);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void salvar() {
		if ((this.usuario != null) && (this.usuario.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}

	public String limparCampos() {
		this.usuario = new Usuario();
		return null;
	}

	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaUsuarios = this.usuarioController.pesquisarPorFiltro(new Filter());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void incluir() {
		try {
			this.usuarioController.incluir(this.usuario);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.USUARIO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizar() {
		try {
			this.usuarioController.alterar(this.usuario);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.USUARIO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void excluir() {
		try {
			if ((this.usuario != null) && (this.usuario.getId() != null)) {
				this.usuarioController.excluir(this.usuario);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.USUARIO.getValor());
			}
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			usuario = new Usuario();
		}
	}

	public List<Perfil> getListaPerfis() {
		return this.listaPerfis;
	}

	public void setListaPerfis(List<Perfil> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	public DataModelUsuario getDataModelUsuario() {
		return this.dataModelUsuario;
	}

	public void setDataModelUsuario(DataModelUsuario dataModelUsuario) {
		this.dataModelUsuario = dataModelUsuario;
	}

	public List<Usuario> getListaUsuarios() {
		return this.listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
