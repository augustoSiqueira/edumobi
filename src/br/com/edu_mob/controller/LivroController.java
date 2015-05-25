package br.com.edu_mob.controller;

import java.util.List;

import org.primefaces.event.FileUploadEvent;

import br.com.edu_mob.entity.Livro;
import br.com.edu_mob.exception.RNException;

public interface LivroController extends GenericController<Livro> {

	public List<Livro> pesquiasarLivrosWeb(String pesquisa) throws RNException;

	public String salvarImagemWeb(String urlImagem) throws RNException;

}
