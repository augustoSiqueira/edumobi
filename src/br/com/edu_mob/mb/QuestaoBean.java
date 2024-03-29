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
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.edu_mob.controller.AlternativaController;
import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.enuns.Letra;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class QuestaoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(QuestaoBean.class
			.getName());

	@ManagedProperty(value = "#{dataModelQuestao}")
	private DataModelQuestao dataModelQuestao;

	private QuestaoController questaoController;
	private AlternativaController alternativaController;
	private CategoriaController categoriaController;
	private AreaConhecimentoController areaConhecimentoController;

	private List<Questao> listaQuestoes = null;
	private List<Alternativa> listaAlternativa = null;
	private List<Alternativa> listaAlternativaExcluir = null;
	private List<Categoria> listaCategoria = null;
	private List<AreaConhecimento> listaAreaConhecimento = null;

	private Questao questao = null;
	private Alternativa alternativa = null;
	private Categoria categoria;
	private boolean atualizado;
	private int aba;

	private Integer nivel = 1;
	
	private String[] letras = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","U","V","W","Y","Z"};

	@PostConstruct
	public void init() {
		Filter filtroQuestao = new Filter();
		Filter filtroCategoria = new Filter();

		filtroCategoria.put("curso", true);
		filtroCategoria.put("ativo", true);
		this.questao = new Questao();
		this.alternativa = new Alternativa();
		this.categoria = new Categoria();
		this.listaAlternativa = new ArrayList<Alternativa>();
		this.listaAlternativaExcluir = new ArrayList<Alternativa>();
		this.questaoController = (QuestaoController) this.getBean(
				"questaoController", QuestaoController.class);
		this.alternativaController = (AlternativaController) this.getBean(
				"alternativaController", AlternativaController.class);
		this.categoriaController = (CategoriaController) this.getBean(
				"categoriaController", CategoriaController.class);
		this.areaConhecimentoController = (AreaConhecimentoController) this
				.getBean("areaConhecimentoController",
						AreaConhecimentoController.class);
		this.dataModelQuestao = new DataModelQuestao();

		try {
			this.listaQuestoes = this.questaoController
					.pesquisarPorFiltro(filtroQuestao);

			this.listaCategoria = this.categoriaController
					.pesquisarPorFiltro(filtroCategoria);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void salvar() {
		if ((this.questao != null) && (this.questao.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}

	public void upload(FileUploadEvent event) {
		try {
			String caminhoImagem = this.questaoController.salvarImagem(event);
			this.questao.setCaminhoImagem(caminhoImagem);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void limparForm() {
		this.limparCampos();
		this.limparCamposAlternativa();
		this.listaAlternativa = new ArrayList<Alternativa>();
		this.nivel = 1;
		this.aba = 0;
	}

	public void limparCampos() {
		this.questao = new Questao();
		this.limparCamposAlternativa();
	}

	public void limparCamposAlternativa() {
		this.alternativa = new Alternativa();
		this.alternativa.setCorreta(false);
	}

	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaQuestoes = this.questaoController
					.pesquisarPorFiltro(new Filter());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void incluir() {

		try {

			this.alternativaController
					.validarAlternativas(this.listaAlternativa);
			this.questao.setNivel(this.nivel);
			this.questaoController.incluir(this.questao);
			this.addMessage(
					MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADA_SUCESSO.getValor(),
					Entidades.QUESTAO.getValor());
			this.atualizarGrid();

			int tamanho = 0;
			int posLetra = 0;

			for (Alternativa alt : this.listaAlternativa) {
				alt.setQuestao(this.listaQuestoes.get(tamanho));
				alt.setLetra(Letra.valueOf(letras[posLetra]));
				this.alternativaController.incluir(alt);
				posLetra++;
			}
			this.atualizarGrid();
			this.listaAlternativa = new ArrayList<Alternativa>();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').hide();");
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void add() {
		this.atualizado = false;

		try {

			if (this.listaAlternativa != null
					&& !this.listaAlternativa.isEmpty()) {

				for (Alternativa alt : this.listaAlternativa) {
					this.alternativaController.validarAlternativasMemoria(
							this.alternativa, this.listaAlternativa);

					if (alt.getResposta()
							.equals(this.alternativa.getResposta())) {
						alt = this.alternativaController
								.alterarEmMemoria(this.alternativa);
						this.atualizado = true;
					}
				}
			}

			if (this.alternativa.getId() == null) {

				this.alternativaController.validarAlternativasMemoria(
						this.alternativa, this.listaAlternativa);
				this.listaAlternativa = this.alternativaController
						.incluirEmMemoria(this.alternativa,
								this.listaAlternativa);

			}

			this.limparCamposAlternativa();
			
		} catch (RNException e) {
			this.alternativa.setCorreta(false);
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void carregarListaAlternativa() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.filtro.put("idQuestao", this.questao.getId().toString());
		try {
			this.listaAlternativa = this.alternativaController
					.pesquisarPorFiltro(this.filtro);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								MensagemUtil.getMensagem(ErrorMessage.ERRO
										.getChave()), msg));
			}
		}

	}

	public void carregarEditar() {
		this.carregarListaAlternativa();
		this.carregarAreaConhecimento();
		this.nivel = this.questao.getNivel();
		this.aba = 0;

	}

	public void remove() {
		this.listaAlternativa = this.alternativaController.excluirEmMemoria(
				this.alternativa, this.listaAlternativa);
		this.limparCamposAlternativa();

	}

	public void atualizar() {
		this.filtro.put("idQuestao", this.questao.getId().toString());

		try {
			this.alternativaController
					.validarAlternativas(this.listaAlternativa);
			

			this.questao.setNivel(this.nivel);
			this.questaoController.alterar(this.questao);
			this.listaAlternativaExcluir = this.alternativaController
					.pesquisarPorFiltro(this.filtro);

			for (Alternativa altExcluir : this.listaAlternativaExcluir) {
				this.alternativaController.excluir(altExcluir);
			}
			
			int posLetra =0;
			for (Alternativa alt : this.listaAlternativa) {
				alt.setQuestao(this.questao);
				alt.setLetra(Letra.valueOf(letras[posLetra]));
				this.alternativaController.incluir(alt);
				posLetra++;
			}

			this.addMessage(
					MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADA_SUCESSO.getValor(),
					Entidades.QUESTAO.getValor());
			
			
			this.atualizarGrid();
			this.limparForm();
			this.listaAlternativaExcluir = new ArrayList<Alternativa>();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').hide();");

		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(
					MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
					e.getListaMensagens());
		}
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			if ((this.questao != null) && (this.questao.getId() != null)) {
				this.filtro.put("idQuestao", this.questao.getId().toString());
				this.listaAlternativaExcluir = this.alternativaController
						.pesquisarPorFiltro(this.filtro);

				for (Alternativa altExcluir : this.listaAlternativaExcluir) {

					this.alternativaController.excluir(altExcluir);
				}

				this.questaoController.excluir(this.questao);

				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO
						.getValor()),
						SucessMessage.EXCLUIDA_SUCESSO.getValor(),
						Entidades.QUESTAO.getValor());
			}
			this.atualizarGrid();
			this.limparForm();
			this.listaAlternativaExcluir = new ArrayList<Alternativa>();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								MensagemUtil.getMensagem(ErrorMessage.ERRO
										.getChave()), msg));
			}
		}
	}

	public void carregarAreaConhecimento(ValueChangeEvent event) {
		Filter filtro = new Filter();
		Categoria categoria = (Categoria) event.getNewValue();
		if ((categoria != null) && (categoria.getId() != null)) {
			filtro.put("idCategoria", categoria.getId().toString());
			try {
				this.listaAreaConhecimento = this.areaConhecimentoController
						.pesquisarPorFiltro(filtro);
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(
						MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
						e.getListaMensagens());
			}
		} else {
			this.categoria = new Categoria();
			this.questao.setAreaConhecimento(null);
		}

	}

	public void carregarAreaConhecimento() {
		Filter filtro = new Filter();

		if ((categoria != null) && (categoria.getId() != null)) {
			filtro.put("idCategoria", categoria.getId().toString());
			try {
				this.listaAreaConhecimento = this.areaConhecimentoController
						.pesquisarPorFiltro(filtro);
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(
						MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()),
						e.getListaMensagens());
			}
		} else {
			this.categoria = new Categoria();
			this.questao.setAreaConhecimento(null);
		}

	}

	public int getAba() {
		return aba;
	}

	public void setAba(int aba) {
		this.aba = aba;
	}

	public DataModelQuestao getDataModelQuestao() {
		return this.dataModelQuestao;
	}

	public void setDataModelQuestao(DataModelQuestao dataModelQuestao) {
		this.dataModelQuestao = dataModelQuestao;
	}

	public List<Questao> getListaQuestoes() {
		return this.listaQuestoes;
	}

	public void setListaQuestoes(List<Questao> listaQuestaos) {
		this.listaQuestoes = listaQuestaos;
	}

	public List<Alternativa> getListaAlternativa() {
		return this.listaAlternativa;
	}

	public void setListaAlternativa(List<Alternativa> listaAlternativa) {
		this.listaAlternativa = listaAlternativa;
	}

	public List<Categoria> getListaCategoria() {
		return this.listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<AreaConhecimento> getListaAreaConhecimento() {
		return this.listaAreaConhecimento;
	}

	public void setListaAreaConhecimento(
			List<AreaConhecimento> listaAreaConhecimento) {
		this.listaAreaConhecimento = listaAreaConhecimento;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
		if ((this.listaQuestoes != null) && !this.listaQuestoes.isEmpty()) {
			this.listaQuestoes.remove(questao);
		}
	}

	public Alternativa getAlternativa() {
		return this.alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Integer getNivel() {
		return this.nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

}
