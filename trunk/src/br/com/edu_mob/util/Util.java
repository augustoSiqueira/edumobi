package br.com.edu_mob.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.edu_mob.dao.impl.GenericDAOImpl;

public class Util {

	private static final Logger logger = Logger.getLogger(GenericDAOImpl.class.getName());

	public static final String FORMATO_DATA_HORA_PT_BR = "dd/MM/yyyy HH:mm:ss";

	/**
	 * Metodo responsavel por converter Data
	 * @param Date valor
	 * @param String formato
	 * @return Date data
	 */

	public static Date parseDate(String valor, String formato) {
		if (valor == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(formato);
		Date data = null;
		try {
			data = format.parse(valor);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Metodo responsavel por converter Data para String
	 * @param date
	 * @param format
	 * @return
	 */

	public static String converteData(Date date, String format) {
		String result = null;
		if ((date != null) && (format != null) && !format.isEmpty()) {
			SimpleDateFormat out = new SimpleDateFormat(format);
			result = out.format(date);
		}
		return result;
	}

	/**
	 * Metodo responsavel por verificar atributo nulo
	 * @param String valor
	 * @return boolean nulo ou nao
	 */

	public static boolean isEmptyOrNull(String s) {
		boolean emptyOrNull = false;
		if ((s == null) || s.isEmpty()) {
			emptyOrNull = true;
		}
		return emptyOrNull;
	}

	/**
	 * Metodo responsavel por criptograr Strings
	 * @param String senha
	 * @return String valor criptografado
	 */

	public static String criptografar(String senha) {
		MessageDigest md = null;
		String senhaCriptografada = "";
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		md.update(senha.getBytes());
		BigInteger hash = new BigInteger(1, md.digest());
		senhaCriptografada = hash.toString(16);
		return senhaCriptografada;
	}

	/**
	 * Metodo para remover caracteres especiais de String
	 *
	 * @param String valor
	 * @return String valor sem caracteres especiais
	 */
	public static String removerCaracteresEspeciais(String valor) {
		String novoValor = "";
		if ((valor != null) && !valor.isEmpty()) {
			novoValor = valor.replaceAll("[^a-zZ-Z0-9 ]", "");
		}
		return novoValor;
	}

	/**
	 * Metodo responsavel por realizar a validacao de CPF.
	 *
	 * @param strCPF numero do CPF a ser validado
	 * @return retornar o resultado da validacao booleana
	 */
	public static boolean validarCpf(String CPF) {

		// removendo máscara para efetuar validação;
		CPF = removerCaracteresEspeciais(CPF);

		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11)) {
			return (false);
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {

			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48); // converte no respectivo caractere
			}
			// numerico
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}
			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
				return (true);
			} else {
				return (false);
			}
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	/**
	 * Metodo responsavel por validar email
	 *
	 * @param String email
	 * @return boolean informando se email valido ou nao
	 */
	public static boolean validarEmail(String email) {
		Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher matcher = pattern.matcher(email);

		return matcher.find();
	}

	/**
	 * Metodo para inserir formatacao de CPF
	 *
	 * @param String cpf
	 * @return String cpf formatado
	 */
	public static String formatarCPF(String cpf) {
		if ((cpf != null) && !cpf.isEmpty()) {
			Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
			Matcher matcher = pattern.matcher(cpf);
			if (matcher.matches()) {
				cpf = matcher.replaceAll("$1.$2.$3-$4");
			}
			return cpf;
		}
		return null;
	}

	/**
	 * Metodo responsavel por gerar senha aleatoria
	 * @param len
	 * @return
	 */

	public static String gerarSenha(int len){
		char[] chart ={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char[] senha= new char[len];
		int chartLenght = chart.length;
		Random rdm = new Random();
		for (int x=0; x<len; x++) {
			senha[x] = chart[rdm.nextInt(chartLenght)];
		}
		return new String(senha);
	}

	/**
	 * Metodo responsavel por gerar matricula dinamicamente do Aluno
	 *
	 * @param long id
	 * @return String matricula
	 */

	public static String gerarMatricula(long id){
		DecimalFormat decimalFormat = new DecimalFormat("00000000000");
		return decimalFormat.format(id);
	}

}
