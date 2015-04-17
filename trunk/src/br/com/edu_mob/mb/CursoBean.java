package br.com.edu_mob.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.exception.RNException;

@ManagedBean
@ViewScoped
public class CursoBean extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Categoria categoria;
	private CategoriaController categoriaController;
	
	@PostConstruct
	public void init() {		
		categoria = new Categoria();
		categoriaController = (CategoriaController) this.getBean("categoriaController", CategoriaController.class);
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("cursoId"))  
        {  
            //Recebe o ID informado  
            long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cursoId").toString());  
            try {
				categoria = categoriaController.pesquisarPorId(id);
			} catch (RNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("meuObjetoId");  
        }  
     }

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}  
    
	
	
	
}
