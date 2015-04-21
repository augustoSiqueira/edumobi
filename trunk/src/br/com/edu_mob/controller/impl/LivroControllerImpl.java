package br.com.edu_mob.controller.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

















import org.primefaces.event.FileUploadEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.CategoriaController;
import br.com.edu_mob.controller.LivroController;
import br.com.edu_mob.dao.CategoriaDAO;
import br.com.edu_mob.dao.LivroDAO;
import br.com.edu_mob.dao.impl.CategoriaDAOImpl;
import br.com.edu_mob.dao.impl.LivroDAOImpl;
import br.com.edu_mob.entity.Categoria;
import br.com.edu_mob.entity.Livro;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.InicializaApp;
import br.com.edu_mob.util.Util;

@Service("livroController")
public class LivroControllerImpl  implements LivroController, Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(LivroDAOImpl.class.getName());
	
	@Autowired
	private LivroDAO livroDAO;
	
	
	@Override
	public Livro pesquisarPorId(Long id) throws RNException {
		Livro livro = null;
		try {
			livro = this.livroDAO.findById(Livro.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return livro;
	}


	@Override
	public List<Livro> listar() throws RNException {
		List<Livro> listaLivros = null;
		try {
			listaLivros = livroDAO.findAll(Livro.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaLivros;
	}


	@Override
	public void incluir(Livro livro) throws RNException {
		try {			
			livro.setDataAtualizacao(new Date());
			livroDAO.save(livro);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		
	}


	@Override
	public void alterar(Livro livro) throws RNException {
		try {			
			livro.setDataAtualizacao(new Date());
			livroDAO.update(livro);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		
	}


	@Override
	public void excluir(Livro livro) throws RNException {
		try {			
			livroDAO.remove(livro);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		
	}


	@Override
	public List<Livro> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Livro> listaLivros = null;
		try {
			listaLivros = this.livroDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaLivros;
	}


	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.livroDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}


	@Override
	public List<Livro> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<Livro> listaLivros = null;
		try {
			listaLivros = this.livroDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaLivros;
	}
	
	private InputStream readJSON(String url) throws IOException, RuntimeException {
        try {
            HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();

            conexao.setReadTimeout(10 * 1000);
            conexao.setConnectTimeout(10 * 1000);
            conexao.setDoInput(true);
            conexao.setRequestMethod("GET");

            conexao.connect();

            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
                return conexao.getInputStream();
            } else {
                return null;
            }
        } catch (RuntimeException excecao) {
            throw excecao;
        }
    }
	
	private String inputStreamToString(InputStream is) throws IOException {
        if (is != null) {
        	 String text = "";

        	    // setup readers with Latin-1 (ISO 8859-1) encoding
        	    BufferedReader i = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        	    int numBytes;
        	    CharBuffer buf = CharBuffer.allocate(1024);
        	    while ((numBytes = i.read(buf)) != -1) {
        	        text += String.copyValueOf(buf.array(), 0, numBytes);
        	        buf.clear();
        	    }

        	    return text;
        } else {
            return null;
        }
    }
	
	public List<Livro> pesquiasarLivrosWeb(String pesquisa) throws RNException{
		
		List<Livro> listaLivros = new ArrayList<Livro>();
		
		pesquisa = pesquisa.replace(" ", "%20");
		StringBuffer url = new StringBuffer()
        .append("https://www.googleapis.com/books/v1/volumes?q=")
        .append(pesquisa)
        .append("&maxResults=40");
		
		try {
			String retorno = inputStreamToString(readJSON(url.toString()));
			
			if(retorno != null || !retorno.isEmpty()){
				 try {
					JSONObject raiz = new JSONObject(retorno);
					JSONArray items = raiz.getJSONArray("items");
					for (int i = 0 ; i < items.length() ; i++) {
						JSONObject item = items.getJSONObject(i);
						JSONObject volumeInfo = item.getJSONObject("volumeInfo");
						
						Livro livro = new Livro();
						//Obtem o nome do livro 
						
						if(volumeInfo.has("title")== true)
							livro.setNome(volumeInfo.getString("title"));
						
						//Obtem os autores do livro 
						livro.setAutor("");
						if(volumeInfo.has("authors")== true){
							JSONArray authors = volumeInfo.getJSONArray("authors");
							for (int j = 0 ; j < authors.length() ; j++) {	
								if(j == authors.length()-1)
									livro.setAutor(livro.getAutor() + authors.getString(j));
								else
									livro.setAutor(livro.getAutor() + authors.getString(j)+ " - ");
							}
							livro.setAutor(livro.getAutor().toUpperCase());
						}
						
						
						if(volumeInfo.has("description") == true){
							String descricao = volumeInfo.getString("description");
							if(descricao.length()>999){
								livro.setDescricao(descricao.substring(0,996)+"...");
							}else{
								livro.setDescricao(descricao);
							}
						}
						if(volumeInfo.has("publisher"))
							livro.setEdicao(volumeInfo.getString("publisher"));
						
						if(volumeInfo.has("imageLinks")){
							JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");							
							livro.setCapa(imageLinks.getString("thumbnail"));							
						}
													
						if(volumeInfo.has("pageCount") == true)
							livro.setQnt_paginas(volumeInfo.getInt("pageCount"));
						
						
						livro.setIsbn("");
						if(volumeInfo.has("industryIdentifiers")){
							JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");						
							for (int k = 0 ; k < industryIdentifiers.length() ; k++) {					
								
								JSONObject ISBN = industryIdentifiers.getJSONObject(k);
								if(k ==industryIdentifiers.length() )
									livro.setIsbn(livro.getIsbn() +ISBN.getString("type") +": "+ ISBN.getString("identifier"));
								else
									livro.setIsbn(livro.getIsbn() +ISBN.getString("type") +": "+ ISBN.getString("identifier") +" / ");
															
							}
							
						}
						
						
						listaLivros.add(livro);
					}
					
					
				} catch (JSONException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					throw new RNException(ErrorMessage.DAO.getChave());
				}
			}
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaLivros;
	}

	
	public String salvarImagemWeb(String urlImagem) throws RNException{
		
		String nomeDoArquivo = Util.criptografar(Util.converteData(new Date(), "yyyy-MM-dd HH:mm:ss.SSSXXX"))+".jpg";
		String arquivo = InicializaApp.CAMINHO_SERVIDOR +"/imagens/"+ nomeDoArquivo;
		URL url;
		try {
			url = new URL(urlImagem);
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf)))
			{
			   out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();

			FileOutputStream fos = new FileOutputStream(arquivo);
			fos.write(response);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return nomeDoArquivo;
	}
	
}
