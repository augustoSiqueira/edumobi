package br.com.edu_mob.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.AreaConhecimentoController;
import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.Util;

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

	private final static String DATA_PADRAO = "01/01/2015 00:00:00";

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
	public List<CategoriaDTO> pesquisarCategorias(@RequestParam(required=true, defaultValue=DATA_PADRAO) String data) {
		List<CategoriaDTO> listaCategoriasDTO = null;
		Filter filtro = new Filter();
		try {
			filtro.put("dataAtualizacao", Util.parseDate(data, Util.FORMATO_DATA_HORA_PT_BR));
			listaCategoriasDTO = this.categoriaController.pesquisarPorFiltroDTO(filtro);
		} catch(RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listaCategoriasDTO;
	}

	@RequestMapping(value="/areasConhecimento.do", method = RequestMethod.GET)
	public List<AreaConhecimentoDTO> pesquisarAreasConhecimento(@RequestParam(required=true, defaultValue=DATA_PADRAO) String data, @RequestParam(required=true, defaultValue="0") String idCategoria) {
		List<AreaConhecimentoDTO> listaAreasConhecimentoDTO = null;
		Filter filtro = new Filter();
		try {
			filtro.put("dataAtualizacao", Util.parseDate(data, Util.FORMATO_DATA_HORA_PT_BR));
			filtro.put("idCategoria", idCategoria);
			listaAreasConhecimentoDTO = this.areaConhecimentoController.pesquisarPorFiltroDTO(filtro);
		} catch(RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listaAreasConhecimentoDTO;
	}

	@RequestMapping(value="/questoes.do", method = RequestMethod.GET)
	public List<QuestaoDTO> pesquisarQuestoes(@RequestParam(required=true, defaultValue=DATA_PADRAO) String data, @RequestParam(required=true, defaultValue="0") String idCategoria) {
		List<QuestaoDTO> listaQuestoesDTO = null;
		Filter filtro = new Filter();
		try {
			filtro.put("dataAtualizacao", Util.parseDate(data, Util.FORMATO_DATA_HORA_PT_BR));
			filtro.put("idCategoria", idCategoria);
			listaQuestoesDTO = this.questaoController.pesquisarPorFiltroDTO(filtro);
		} catch(RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listaQuestoesDTO;
	}

}
