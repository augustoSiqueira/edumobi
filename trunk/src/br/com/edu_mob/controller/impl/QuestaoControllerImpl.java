package br.com.edu_mob.controller.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.edu_mob.controller.QuestaoController;
import br.com.edu_mob.dao.AlternativaDAO;
import br.com.edu_mob.dao.QuestaoDAO;
import br.com.edu_mob.dao.impl.QuestaoDAOImpl;
import br.com.edu_mob.entity.Questao;
import br.com.edu_mob.entity.enuns.Letra;
import br.com.edu_mob.exception.DAOException;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.services.AlternativaDTO;
import br.com.edu_mob.services.QuestaoDTO;
import br.com.edu_mob.util.Filter;
import br.com.edu_mob.util.InicializaApp;
import br.com.edu_mob.util.Util;


@Service("questaoController")
public class QuestaoControllerImpl implements QuestaoController{

	private static final Logger logger = Logger.getLogger(QuestaoDAOImpl.class.getName());

	@Autowired
	private QuestaoDAO questaoDAO;

	@Autowired
	private AlternativaDAO alternativaDAO;

	@Override
	public List<Questao> listar() throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.findAll(Questao.class);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}

	@Override
	public Questao pesquisarPorId(Long id) throws RNException {
		Questao questao = null;
		try {
			questao = this.questaoDAO.findById(Questao.class, id);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return questao;
	}

	@Override
	public void incluir(Questao questao) throws RNException {
		try {
			questao.setDataAtualizacao(new Date());

			this.questaoDAO.save(questao);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	private Questao atribuirLetra(Questao questao){
		String[] letras = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","U","V","W","Y","Z"};

		if((questao.getListaAlternativas() != null) && (questao.getListaAlternativas().size()>0)){
			for (int i = 0; i < questao.getListaAlternativas().size(); i++) {
				questao.getListaAlternativas().get(i).setLetra(Letra.valueOf(letras[i]));
			}
		}
		return questao;
	}

	@Override
	public String salvarImagem(FileUploadEvent event) throws RNException{

		//String nomeDoArquivo = event.getFile().getFileName();
		String nomeDoArquivo = Util.criptografar(Util.converteData(new Date(), "yyyy-MM-dd HH:mm:ss.SSSXXX"))+event.getFile().getFileName();
		try {
			File diretorio = new File(InicializaApp.CAMINHO_SERVIDOR +"/imagens/");
			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}

			String arquivo = InicializaApp.CAMINHO_SERVIDOR +"/imagens/"+ nomeDoArquivo;
			InputStream inputStream = event.getFile().getInputstream();
			OutputStream outputStream = new FileOutputStream(arquivo);
			System.out.println(arquivo);
			int read = 0;
			long tamanho = event.getFile().getSize();
			byte[] arquivoByte = new byte[(int)tamanho];

			while ((read = inputStream.read(arquivoByte)) != -1) {
				outputStream.write(arquivoByte, 0, read);
			}

			inputStream.close();
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}

		return nomeDoArquivo;

	}

	@Override
	public void alterar(Questao questao) throws RNException {
		try {
			questao.setDataAtualizacao(new Date());
			this.questaoDAO.update(questao);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public void excluir(Questao questao) throws RNException {

		try {
			this.questaoDAO.remove(questao);
		} catch (DataAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
	}

	@Override
	public List<Questao> pesquisarPorFiltro(Filter filtro) throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.pesquisarPorFiltro(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}

	@Override
	public List<QuestaoDTO> pesquisarPorFiltroDTO(Filter filtro) throws RNException {
		List<Questao> listaQuestoes = null;
		List<QuestaoDTO> listaQuestoesDTO = null;
		listaQuestoesDTO = new ArrayList<QuestaoDTO>();
		try {
			listaQuestoes = this.questaoDAO.pesquisarPorFiltroDTO(filtro);
				for (Questao q : listaQuestoes) {
					QuestaoDTO questao = new QuestaoDTO(q.getId(), q.getEnunciado(), q.getObservacao(), q.getCaminhoImagem(), q.getAreaConhecimento().getId(),q.getDataAtualizacao());  
					if(q.getListaAlternativas()!= null && !q.getListaAlternativas().isEmpty()){
						questao.setListaAlternativasDTO(q.getListaAlternativas());
					}
					listaQuestoesDTO.add(questao);
				}
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoesDTO;
	}

	@Override
	public int pesquisarPorFiltroCount(Filter filtro) throws RNException {
		int retorno = 0;
		try {
			retorno = this.questaoDAO.pesquisarPorFiltroCount(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return retorno;
	}

	@Override
	public List<Questao> pesquisarPorFiltroPaginada(Filter filtro,
			int primeiroReg, int paginaSize) throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.pesquisarPorFiltroPaginada(filtro, primeiroReg, paginaSize);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}

	@Override
	public int pesquisarQtdTotalQuestoes(Filter filtro) throws RNException {
		int qtdTotal = 0;
		try {
			qtdTotal = this.questaoDAO.pesquisarQtdTotalQuestoes(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return qtdTotal;
	}

	@Override
	public List<Questao> pesquisarSimulado(Filter filtro) throws RNException {
		List<Questao> listaQuestoes = null;
		try {
			listaQuestoes = this.questaoDAO.pesquisarSimulado(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoes;
	}

	@Override
	public List<QuestaoDTO> pesquisarSimuladoDTO(Filter filtro) throws RNException {
		List<QuestaoDTO> listaQuestoesDTO = null;
		try {
			listaQuestoesDTO = this.questaoDAO.pesquisarSimuladoDTO(filtro);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RNException(ErrorMessage.DAO.getChave());
		}
		return listaQuestoesDTO;
	}

}
