package br.com.edu_mob.mb;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.controller.RespostaEstudoController;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.RespostaEstudo;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.util.Filter;

@ManagedBean
@ViewScoped
public class DataModelRespostaEstudo extends LazyDataModel<Questao> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DataModelRespostaEstudo.class.getName());

	private QuestaoController questaoController;

	private RespostaEstudoController respostaEstudoController;

	private List<Questao> listaQuestoes;

	private static int QTD_REGISTROS = 4;

	@Override
	public Questao getRowData(String rowKey) {
		for (Questao questao : this.listaQuestoes) {
			if (questao.getId().equals(rowKey)) {
				return questao;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Questao questao) {
		return questao.getId();
	}

	@Override
	public List<Questao> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		WebApplicationContext webAppContext = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		this.questaoController = webAppContext.getBean("questaoController", QuestaoController.class);
		this.respostaEstudoController = webAppContext.getBean("respostaEstudoController", RespostaEstudoController.class);
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String letras[] = {"A", "B", "C", "D", "E"};
		Filter filtro = new Filter();
		List<RespostaEstudo> listaRespostaEstudo = null;
		try {
			filtro.putAll(filters);
			filtro.put("idAreaConhecimento", facesContext.getExternalContext().getRequestParameterMap().get("idAreaConhecimento").toString());
			this.listaQuestoes = this.questaoController.pesquisarPorFiltroPaginada(filtro, first, QTD_REGISTROS);
			if(((this.listaQuestoes != null) && !this.listaQuestoes.isEmpty())) {
				for (Questao questao : this.listaQuestoes) {
					questao.setNumero(++first);
					Filter filtroResposta = new Filter();
					filtroResposta.put("idQuestao", questao.getId().toString());
					filtroResposta.put("idUsuario", usuario.getId().toString());
					listaRespostaEstudo = this.respostaEstudoController.pesquisarPorFiltro(filtroResposta);
					if((listaRespostaEstudo != null) && !listaRespostaEstudo.isEmpty()) {
						questao.setCorreta(listaRespostaEstudo.get(0).isCorreta());
					}
					for (int i = 0; i < questao.getListaAlternativas().size(); i++) {
						questao.getListaAlternativas().get(i).setResposta(letras[i] + " - " + questao.getListaAlternativas().get(i).getResposta());
					}
				}
			}
			this.setRowCount(this.questaoController.pesquisarPorFiltroCount(filtro));
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return this.listaQuestoes;
	}

}
