package br.com.edu_mob.mb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;



import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;








import org.primefaces.model.UploadedFile;

import com.sun.media.sound.AlawCodec;

import br.com.edu_mob.controller.AlternativaController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Alternativa;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class QuestaoBean extends GenericBean implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(QuestaoBean.class.getName());

	@ManagedProperty(value = "#{dataModelQuestao}")
	private DataModelQuestao dataModelQuestao;

	private QuestaoController questaoController;
	private AlternativaController alternativaController;
	private List<Questao> listaQuestoes = null;
	
	private Alternativa alternativaA, alternativaB, alternativaC, alternativaD = null;
	
	
	
	


	private Questao questao = null;

	@PostConstruct
	public void init() {
		Filter filtroQuestao = new Filter();
		this.questao = new Questao();
		this.alternativaA = new Alternativa();
		this.alternativaB = new Alternativa();
		this.alternativaC = new Alternativa();
		this.alternativaD = new Alternativa();
		this.alternativaA.setCorreta(false);
		this.alternativaB.setCorreta(false);
		this.alternativaC.setCorreta(false);
		this.alternativaD.setCorreta(false);
		this.questaoController = (QuestaoController) this.getBean("questaoController", QuestaoController.class);
		this.alternativaController = (AlternativaController) this.getBean("alternativaController", AlternativaController.class);
		this.dataModelQuestao = new DataModelQuestao();
		try {
			this.listaQuestoes = this.questaoController.pesquisarPorFiltro(filtroQuestao);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void salvar() {
		if ((this.questao != null) && (this.questao.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}
	
	
	public void upload(FileUploadEvent event){
		  
		/* UploadedFile arq = event.getFile();
		 this.questao.setCaminhoImagem(arq.getFileName());
		 
		 ServletContext context = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext(); 
		 File file = new File(  
	                context.getRealPath("/restrito/"+ new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
		 
		 if (!file.exists()) {  
	            file.mkdirs();  
	        }  
	  
	        String caminho = file + "\\" + arq.getFileName(); 
	        FileOutputStream outputStream; 
	        
	        try {  
	            outputStream = new FileOutputStream(caminho);  
	            outputStream.write(file);  
	            outputStream.close();  
	        } catch (FileNotFoundException e) {  
	            System.out.println(e.getMessage());  
	        } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	        }  
		 
		 */
		 
		 
	}
	

	public void limparCampos() {
		this.questao = new Questao();
		this.alternativaA = new Alternativa();
		this.alternativaB = new Alternativa();
		this.alternativaC = new Alternativa();
		this.alternativaD = new Alternativa();
	}

	public void atualizarGrid() {
		try {
			this.limparCampos();
			this.listaQuestoes = this.questaoController.pesquisarPorFiltro(new Filter());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}

	public void incluir() {
		
		
		try {
			
			this.questaoController.incluir(this.questao);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.CADASTRADA_SUCESSO.getValor(), Entidades.QUESTAO.getValor());
			this.atualizarGrid();
			int tamanho = listaQuestoes.size() - 1;
			this.alternativaA.setQuestao(listaQuestoes.get(tamanho));
			this.alternativaB.setQuestao(listaQuestoes.get(tamanho));
			this.alternativaC.setQuestao(listaQuestoes.get(tamanho));
			this.alternativaD.setQuestao(listaQuestoes.get(tamanho));
			
			this.alternativaController.incluir(alternativaA);
			this.alternativaController.incluir(alternativaB);
			this.alternativaController.incluir(alternativaC);
			this.alternativaController.incluir(alternativaD);
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
		
		
		
		
		
	}

	public void atualizar() {
		
		try {
			this.questaoController.alterar(this.questao);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADA_SUCESSO.getValor(), Entidades.QUESTAO.getValor());
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(ErrorMessage.ERRO.getChave(), e.getListaMensagens());
		}
		
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if ((this.questao != null) && (this.questao.getId() != null)) {
				this.questaoController.excluir(this.questao);
				this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
						SucessMessage.EXCLUIDA_SUCESSO.getValor(), Entidades.QUESTAO.getValor());
			}
			this.atualizarGrid();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			for (String msg : e.getListaMensagens()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), msg));
			}
		}
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

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
		if((this.listaQuestoes != null) && !this.listaQuestoes.isEmpty()) {
			this.listaQuestoes.remove(questao);
		}
	}
	
	public Alternativa getAlternativaA(){
		return this.alternativaA;
	}
	
	public void setAlternativaA(Alternativa alternativaA){
		this.alternativaA = alternativaA;
	}
	
	public Alternativa getAlternativaB(){
		return this.alternativaB;
	}
	
	public void setAlternativaB(Alternativa alternativaB){
		this.alternativaB = alternativaB;
	}
	
	public Alternativa getAlternativaC(){
		return this.alternativaC;
	}
	
	public void setAlternativaC(Alternativa alternativaC){
		this.alternativaC = alternativaC;
	}
	
	public Alternativa getAlternativaD(){
		return this.alternativaD;
	}
	
	public void setAlternativaD(Alternativa alternativaD){
		this.alternativaD = alternativaD;
	}
	
	
}
