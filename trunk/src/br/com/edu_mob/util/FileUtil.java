package br.com.edu_mob.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import br.com.edu_mob.exception.GenericException;
import br.com.edu_mob.message.ErrorMessage;

/**
 * Classe auxiliar para manipulacao de arquivos
 * 
 */

public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class.getName());


	private static final String CAMINHO_PASTA_IMAGENS = "imagemQuestoes";

	public static final String PROPERTIES_CONFIG = "config.properties";

	// Tamanho em bytes
	public static final long TAMANHO_LIMITE = 2097152L;

	/**
	 * Metodo responsavel por carregar um arquivo no formato properties
	 * 
	 * @param String nome
	 * @return Properties
	 */

	public static Properties carregarProperties(String nome) {
		Properties properties = null;
		try {
			Resource resource = new ClassPathResource(File.separator + nome);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return properties;
	}

	/**
	 * Metodo para criptografar a Senha do usuario do sistema.
	 * 
	 * @param senha
	 * @return
	 */
	public static String criptografar(String senha) {
		String sha1 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] result = md.digest(senha.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			sha1 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return sha1;
	}

	/**
	 * Metodo responsavel por realizar upload do arquivo
	 * 
	 * @param HttpServletResponse response
	 * @param String arquivo
	 * @param String nomeOriginal
	 * @param Class entidade
	 * @throws GenericException
	 */

	public static void baixarArquivo(HttpServletResponse response, String arquivo, String nomeOriginal, String caminho) throws GenericException {
		try {
			
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + nomeOriginal);
			File file = new File(caminho + CAMINHO_PASTA_IMAGENS + nomeOriginal);
			FileInputStream fileInputStream = new FileInputStream(file);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			response.setContentLength((int) file.length());
			byte[] outputByte = new byte[4096];
			while (fileInputStream.read(outputByte, 0, 4096) != -1) {
				servletOutputStream.write(outputByte, 0, 4096);
			}
			FileCopyUtils.copy(fileInputStream, response.getOutputStream());
			fileInputStream.close();
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new GenericException(ErrorMessage.ARQUIVO_NAO_ENCONTRADO.getChave());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Metodo responsavel por validar o arquivo enviado
	 * 
	 * @param CommonsMultipartFile multipartFile
	 * @throws GenericException
	 */


	public static void validarArquivo(CommonsMultipartFile multipartFile) throws GenericException {
		String nome = "";
		String extensao = "";
		try {
			if ((multipartFile.getSize() > TAMANHO_LIMITE)) {
				throw new GenericException(ErrorMessage.ARQUIVO_TAMANHO_LIMITE.getChave());
			}
			nome = multipartFile.getOriginalFilename();
			if (!nome.isEmpty()) {
				extensao = retornarExtensaoArquivo(nome);
				if (!validarExtensao(extensao)) {
					throw new GenericException(ErrorMessage.ARQUIVO_TIPO_NAO_SUPORTADO.getChave());
				}
			}
		} catch (IllegalStateException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Metodo responsavel por gravar arquivo em diretorio informado
	 * 
	 * @param nome
	 * @param extensao
	 * @param destino
	 * @param entidade
	 * @param multipartFile
	 * @throws GenericException
	 */

	public static void salvarArquivoDiretorio(String nome, String extensao,
			MultipartFile multipartFile) {
		String destino = "";
		File file = null;
		try {
			destino = CAMINHO_PASTA_IMAGENS;
			file = new File(destino + nome);
			file.mkdirs();
			multipartFile.transferTo(new File(destino + nome));
		} catch (IllegalStateException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Metodo responsavel por validar extensao de arquivo
	 * 
	 * @param extension
	 * @return
	 */
	public static boolean validarExtensao(String extension) {
		boolean isValid = false;

		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add("png");
		extensions.add("jpg");
		extensions.add("gif");

		if (extensions.contains(extension.toLowerCase())) {
			isValid = true;
		}

		return isValid;
	}

	/**
	 * Metodo responsavel por retornar a extensao do arquivo informado
	 * 
	 * @param String nomeArquivo
	 * @return extensao com ponto String
	 */
	public static String retornarExtensaoArquivo(String nomeArquivo) {
		String retorno = "";
		if ((nomeArquivo != null) && !nomeArquivo.isEmpty()) {
			retorno = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1, nomeArquivo.length());
		}
		return retorno;
	}

	/**
	 * Metodo responsavel por deletar um arquivo
	 * 
	 * @param String caminho
	 * @return true or false
	 */

	public static boolean excluirArquivo(String caminho) {
		File file = new File(caminho);
		return file.delete();
	}

}
