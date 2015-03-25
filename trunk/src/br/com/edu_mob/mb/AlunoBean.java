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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.MunicipioController;
import br.com.edu_mob.controller.UFController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.entity.Municipio;
import br.com.edu_mob.entity.UF;
import br.com.edu_mob.entity.enuns.Sexo;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.AliasNavigation;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class AlunoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AlunoBean.class.getName());

	@ManagedProperty(value = "#{dataModelAluno}")
	private DataModelAluno dataModelAluno;

	private AlunoController alunoController;

	private MunicipioController municipioController;

	private UFController ufController;

	private List<Aluno> listaAlunos = null;

	private List<Municipio> listaMunicipios = null;

	private List<UF> listaUF = null;

	private static final String ID_UF = "1";

	private Aluno aluno = new Aluno();

	private UF uf;

	@PostConstruct
	public void init() {
		Filter filtroMunicipio = new Filter();
		filtroMunicipio.put("idUF", ID_UF);
		this.aluno = new Aluno();
		this.uf = new UF();
		this.alunoController = (AlunoController) this.getBean("alunoController", AlunoController.class);
		this.municipioController = (MunicipioController) this.getBean("municipioController", MunicipioController.class);
		this.ufController = (UFController) this.getBean("uFController", UFController.class);
		this.dataModelAluno = new DataModelAluno();
		try {
			this.listaAlunos = this.alunoController.pesquisarPorFiltro(new Filter());
			this.listaUF = this.ufController.pesquisarPorFiltro(new Filter());
			this.listaMunicipios = this.municipioController.pesquisarPorFiltro(filtroMunicipio);
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

	public void salvar() {
		if ((this.aluno != null) && (this.aluno.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}

	public String cadastrarPreviamente() {
		if ((this.aluno != null) && (this.aluno.getId() == null)) {
			this.incluirPreviamente();
			return AliasNavigation.LOGIN;
		}
		return null;
	}

	public void limparCampos() {
		this.aluno = new Aluno();
		this.uf = new UF();
	}

	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaAlunos = this.alunoController.pesquisarPorFiltro(new Filter());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void incluirPreviamente() {
		try {
			this.alunoController.incluirPreviamente(this.aluno);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void incluir() {
		try {
			this.alunoController.incluir(this.aluno);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void atualizar() {
		try {
			this.alunoController.alterar(this.aluno);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if ((this.aluno != null) && (this.aluno.getId() != null)) {
				this.alunoController.excluir(this.aluno);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
			}
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), msg));
			}
		}
	}

	public void atualizarMunicipioEdicao(Aluno aluno) {
		if((aluno != null) && (aluno.getMunicipio() != null) && (aluno.getMunicipio().getId() != null)) {
			Filter filtro = new Filter();
			filtro.put("idUF", aluno.getMunicipio().getUf().getId().toString());
			try {
				this.listaMunicipios = this.municipioController.pesquisarPorFiltro(filtro);
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			}
		}
	}

	public DataModelAluno getDataModelAluno() {
		return this.dataModelAluno;
	}

	public void setDataModelAluno(DataModelAluno dataModelAluno) {
		this.dataModelAluno = dataModelAluno;
	}

	public List<Aluno> getListaAlunos() {
		return this.listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
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

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
		this.atualizarMunicipioEdicao(aluno);
	}

	public SelectItem[] getListaSexo() {
		SelectItem[] items = new SelectItem[Sexo.values().length];
		items[0] = new SelectItem(Sexo.MASCULINO, Sexo.MASCULINO.getLabel());
		items[1] = new SelectItem(Sexo.FEMININO, Sexo.FEMININO.getLabel());
		return items;
	}

	public UF getUf() {
		return this.uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

}
