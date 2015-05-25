package br.com.edu_mob.mb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.AliasNavigation;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.MensagemUtil;


@ManagedBean
@ViewScoped
public class MenuCategoriaBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CategoriaBean.class.getName());

	private TreeNode root;
	
	private CategoriaController categoriaController;
	private List<Categoria> listaCategorias = null;
	
	@PostConstruct
	public void init() {
		Filter filtroCategoria = new Filter();
		filtroCategoria.put("ativo", Boolean.TRUE);
		
		this.categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);

		try {
			this.listaCategorias = this.categoriaController.pesquisarPorFiltro(filtroCategoria);
			
			root = new DefaultTreeNode("Root", null);
			
			for (Categoria categoria : listaCategorias) {
				if(categoria.getPai() == null && categoria.getSubCategorias().size() > 0){
					adicionarNos(categoria, root);
				}else if(categoria.getPai() == null && categoria.isCurso() == true && categoria.getSubCategorias().size() == 0){
					adicionarNos(categoria, root);					
				}
			}
			
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}	
	}
	
	

	 private TreeNode adicionarNos(Categoria categoria, TreeNode  treeNodepai) {
		 TreeNode retorno = null;		 
		 retorno = new DefaultTreeNode(categoria, treeNodepai);
		 for (Categoria filho : categoria.getSubCategorias()) {
			adicionarNos(filho, retorno);
		 }		 
		 return retorno;
	 }
	 
	public TreeNode getRoot() {
		return root;
	}

	public CategoriaController getCategoriaController() {
		return categoriaController;
	}

	public void setCategoriaController(CategoriaController categoriaController) {
		this.categoriaController = categoriaController;
	}

	public static Logger getLogger() {
		return logger;
	}


}