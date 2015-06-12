package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.AliasNavigation;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class CadastroPrevioAlunoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CadastroPrevioAlunoBean.class.getName());

	private AlunoController alunoController;

	private CategoriaController categoriaController;

	private List<Categoria> listaCursos = new ArrayList<Categoria>();

	private Categoria curso = new Categoria();

	private Aluno aluno = new Aluno();

	@PostConstruct
	public void init() {
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		this.alunoController = (AlunoController) this.getBean("alunoController", AlunoController.class);
		this.aluno = new Aluno();

		Filter filtroCategoria = new Filter();
		filtroCategoria.put("ativo", Boolean.TRUE);
		filtroCategoria.put("curso", Boolean.TRUE);
		try {
			this.listaCursos = this.categoriaController.pesquisarPorFiltro(filtroCategoria);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public String incluirPreviamente() {
		try {
			if(this.aluno == null) {
				this.aluno = new Aluno();
			}
			this.aluno.setNome(this.aluno.getNome().trim());
			this.aluno.setEmail(this.aluno.getEmail().trim());
			List<Categoria> cursosGratuito = new ArrayList<Categoria>();
			cursosGratuito.add(this.curso);
			this.aluno.setCursos( cursosGratuito);
			this.alunoController.incluirPreviamente(this.aluno);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADO_SUCESSO.getValor(), Entidades.ALUNO.getValor());
			this.aluno = new Aluno();
			return AliasNavigation.LOGIN;
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			return AliasNavigation.PAGINA_CADASTRO_ALUNO;
		}
	}

	public List<Categoria> getListaCursos() {
		return this.listaCursos;
	}

	public void setListaCursos(List<Categoria> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public Categoria getCurso() {
		return this.curso;
	}

	public void setCurso(Categoria curso) {
		this.curso = curso;
	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
