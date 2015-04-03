package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.MunicipioController;
import br.com.edu_mob.controller.UFController;
import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.entity.Municipio;
import br.com.edu_mob.entity.UF;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.entity.enuns.Sexo;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;
import br.com.edu_mob.util.Util;

@ManagedBean
@ViewScoped
public class DadosPessoaisBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DadosPessoaisBean.class.getName());

	private UsuarioController usuarioController;

	private AlunoController alunoController;

	private MunicipioController municipioController;

	private UFController ufController;

	private List<Municipio> listaMunicipios = null;

	private List<UF> listaUF = null;

	private Usuario usuario;

	private Aluno aluno;

	private String novaSenha;

	private String confirmacaoSenha;

	private UF uf;

	private final static long ID_ALUNO = 2L;

	private static final String ID_UF = "1";

	@PostConstruct
	public void init() {
		Filter filtroMunicipio = new Filter();
		filtroMunicipio.put("idUF", ID_UF);
		this.uf = new UF();
		this.usuarioController = (UsuarioController) this.getBean("usuarioController", UsuarioController.class);
		this.alunoController = (AlunoController) this.getBean("alunoController", AlunoController.class);
		this.municipioController = (MunicipioController) this.getBean("municipioController", MunicipioController.class);
		this.ufController = (UFController) this.getBean("uFController", UFController.class);
		try {
			this.listaUF = this.ufController.pesquisarPorFiltro(new Filter());
			this.listaMunicipios = this.municipioController.pesquisarPorFiltro(filtroMunicipio);
			this.editarDadosPessoais();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void ufChangeListener(ValueChangeEvent event) {
		Filter filtro = new Filter();
		UF uf = (UF) event.getNewValue();
		if((uf != null) && (uf.getId() != null)) {
			filtro.put("idUF", uf.getId().toString());
			try {
				this.listaMunicipios = this.municipioController.pesquisarPorFiltro(filtro);
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			}
		} else {
			this.uf = new UF();
			this.aluno.setMunicipio(null);
		}
	}

	public void editarDadosPessoais() {
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(usuario.getPerfil().getId().equals(ID_ALUNO)) {
				this.aluno = this.alunoController.pesquisarPorId(usuario.getId());
				this.atualizarMunicipioEdicao(this.aluno);
			} else {
				this.usuario = usuario;
			}
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizarAluno() {
		try {
			this.validarAlteracaoSenha();
			if ((this.novaSenha != null) && !this.novaSenha.isEmpty()) {
				this.aluno.setSenha(Util.criptografar(this.novaSenha));
			}
			this.alunoController.alterar(this.aluno);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizarUsuario() {
		try {
			this.validarAlteracaoSenha();
			if(((this.novaSenha != null) && !this.novaSenha.isEmpty())) {
				this.usuario.setSenha(Util.criptografar(this.novaSenha));
			}
			this.usuarioController.alterar(this.usuario);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void validarAlteracaoSenha() {
		if(((this.novaSenha != null) && !this.novaSenha.isEmpty()) && ((this.confirmacaoSenha != null) && !this.confirmacaoSenha.isEmpty()) && (!this.novaSenha.equals(this.confirmacaoSenha))) {
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), ErrorMessage.DADOS_PESSOAIS_SENHA_NAO_CONFERE.getChave());
		}
	}

	public void atualizarMunicipioEdicao(Aluno aluno) {
		if((aluno != null) && (aluno.getMunicipio() != null) && (aluno.getMunicipio().getId() != null)) {
			Filter filtro = new Filter();
			this.uf = aluno.getMunicipio().getUf();
			filtro.put("idUF", aluno.getMunicipio().getUf().getId().toString());
			try {
				this.listaMunicipios = this.municipioController.pesquisarPorFiltro(filtro);
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			}
		}
	}

	public void limparCamposUsuario() {
		this.usuario = new Usuario();
	}

	public void limparCamposAluno() {
		this.aluno = new Aluno();
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getNovaSenha() {
		return this.novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmacaoSenha() {
		return this.confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public List<Municipio> getListaMunicipios() {
		return this.listaMunicipios;
	}

	public void setListaMunicipios(List<Municipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public List<UF> getListaUF() {
		return this.listaUF;
	}

	public void setListaUF(List<UF> listaUF) {
		this.listaUF = listaUF;
	}

	public UF getUf() {
		return this.uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public SelectItem[] getListaSexo() {
		SelectItem[] items = new SelectItem[Sexo.values().length];
		items[0] = new SelectItem(Sexo.MASCULINO, Sexo.MASCULINO.getLabel());
		items[1] = new SelectItem(Sexo.FEMININO, Sexo.FEMININO.getLabel());
		return items;
	}

}
