package br.com.edu_mob.mb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.LivroController;
import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Livro;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.Entidades;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.message.SucessMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;
import br.com.edu_mob.util.UtilSession;

@ManagedBean
@ViewScoped
public class LivrosBean extends GenericBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LivrosBean.class.getName());
	
	private Livro livro;
	private Categoria categoria;
	private LivroController livroController;
	private CategoriaController categoriaController;
	private QuestaoController questaoController;
	private String pesquisa;
	
	private List<Livro> listaLivrosWeb;
	
	private List<Livro> listaLivros;
	
	@ManagedProperty(value = "#{dataModelLivro}")
	private DataModelLivro dataModelLivro;
	
	private boolean pesquisaWeb;
	
	@PostConstruct
	public void init() {
		
		livro = new Livro();
		listaLivrosWeb = new ArrayList<Livro>();
		dataModelLivro = new DataModelLivro();
		livroController = (LivroController) this.getBean("livroController", LivroController.class);
		categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		questaoController = (QuestaoController) this.getBean("questaoController", QuestaoController.class);
		
		categoria = (Categoria) UtilSession.getHttpSessionObject("categoriaId");
		pesquisa = categoria.getNome();
				
		try {
			listaLivros = livroController.listar();
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void pesquisaWeb(){
		
		try {
			
			Filter filtro = new Filter();
			//filtro.put("web", getPesquisa()); //pesquisar por nome e descrição
			filtro.put("nome", getPesquisa());
			
			if(isPesquisaWeb() == false)
				listaLivrosWeb = livroController.pesquisarPorFiltro(filtro);
			else
				listaLivrosWeb = livroController.pesquiasarLivrosWeb(getPesquisa());
			
			Filter filtro2 = new Filter();
			filtro2.put("idCategoria", getCategoria().getId().toString());
			listaLivrosWeb.removeAll(livroController.pesquisarPorFiltro(filtro2));
			
			//filtro.put("web", getPesquisa()); //pesquisar por nome e descrição
			filtro.put("nome", getPesquisa());
			// baixar e salvar no banco
			//listaLivrosWeb = livroController.pesquiasarLivrosWeb(getPesquisa());
			/*for (Livro livro : listaLivrosWeb) {
				if(livro.getCapa() != null && !livro.getCapa().equals("")){
				livro.setCapa(livroController.salvarImagemWeb(livro.getCapa()));
				livro.setCategoria(null);
				try{
				livroController.incluir(livro);
				}catch (Exception e){
					
					ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
			                .getExternalContext().getContext();
			        String servidor = ctx.getRealPath("/");
					String nome = servidor+"/imagens/"+livro.getCapa(); 
					File f = new File(nome); 
					try{
						f.delete();						
					}catch (Exception ex){
						System.out.println("Erro ao excluir: "+nome);
					}
					
				}
				}
			}*/
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public boolean isPesquisaWeb(){
		if(getPesquisa() != null && getPesquisa().trim().length() > 1 && getPesquisa().trim().substring(0, 1).equals("#") ){
			return true;
		}
		return false;
	}
	public void addLivroWeb(){
		
		try {
			if(isPesquisaWeb() == true)
				livro.setCapa(livroController.salvarImagemWeb(livro.getCapa()));
			
			livro.setCategoria(categoria);
			livro.setId(null);
			livroController.incluir(livro);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.LIVRO.getValor());
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').hide();");
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void excluirLivro(){
		
		try {
			livroController.excluir(getLivro());
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.EXCLUIDO_SUCESSO.getValor(), Entidades.LIVRO.getValor());
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
	}
	
	public void popUpAdd(){
		this.livro = new Livro();
		this.listaLivrosWeb = null;
	}
		
	public void cancelar(){
		livro = new Livro();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dlg2').hide();");
		
	}
	public void upload(FileUploadEvent event){
		try{
			String caminhoImagem = this.questaoController.salvarImagem(event);
			livro.setCapa(caminhoImagem);
			} catch (RNException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
			}
	}
	
	public void salvar() {
		if ((this.livro != null) && (this.livro.getId() != null)) {
			this.atualizar();
		} else {
			this.incluir();
		}
	}
	
	public void atualizar() {
		
		try {
			this.livroController.alterar(livro);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADA_SUCESSO.getValor(), Entidades.LIVRO.getValor());
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg2').hide();");
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}
		
	}
	
	public void incluir() {
		
		try {
			livro.setCategoria(categoria);
			livroController.incluir(livro);
			this.addMessage(MensagemUtil.getMensagem(SucessMessage.SUCESSO.getValor()),
					SucessMessage.ATUALIZADO_SUCESSO.getValor(), Entidades.LIVRO.getValor());
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg2').hide();");
		} catch (RNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<Livro> getListaLivrosWeb() {
		return listaLivrosWeb;
	}

	public void setListaLivrosWeb(List<Livro> listaLivrosWeb) {
		this.listaLivrosWeb = listaLivrosWeb;
	}

	public List<Livro> getListaLivros() {
		return listaLivros;
	}

	public void setListaLivros(List<Livro> listaLivros) {
		this.listaLivros = listaLivros;
	}

	public DataModelLivro getDataModelLivro() {
		return dataModelLivro;
	}

	public void setDataModelLivro(DataModelLivro dataModelLivro) {
		this.dataModelLivro = dataModelLivro;
	}
	
	
	
	
}
