package br.com.edu_mob.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.entity.AreaConhecimento;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@RestController
@RequestMapping(value="/services")
public class Services  {

	private static final Logger logger = Logger.getLogger(Services.class.getName());

	@Autowired
	private AlunoController alunoController;

	@Autowired
	private CategoriaController categoriaController;

	@Autowired
	private AreaConhecimentoController areaConhecimentoController;

	@Autowired
	private QuestaoController questaoController;

	@RequestMapping(value = "/validarLogin.do", method = RequestMethod.GET)
	public Aluno validarLogin(String email, String senha) {
		Aluno aluno = null;
		try {
			aluno = this.alunoController.validarAcessoServico(email, senha);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return aluno;
	}

	@RequestMapping(value="/categorias.do", method = RequestMethod.GET)
	public List<Categoria> pesquisarCategorias() {
		List<Categoria> listaCategorias = null;
		try {
			listaCategorias = this.categoriaController.pesquisarPorFiltro(new Filter());
		} catch(RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listaCategorias;
	}

	@RequestMapping(value="/areasConhecimento.do", method = RequestMethod.GET)
	public List<AreaConhecimento> pesquisarAreasConhecimento() {
		List<AreaConhecimento> listaAreasConhecimento = null;
		try {
			listaAreasConhecimento = this.areaConhecimentoController.pesquisarPorFiltro(new Filter());
		} catch(RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listaAreasConhecimento;
	}

	@RequestMapping(value="/questoes.do", method = RequestMethod.GET)
	public List<Questao> pesquisarQuestoes() {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoController.pesquisarPorFiltro(new Filter());
		} catch(RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listaQuestoes;
	}

}
