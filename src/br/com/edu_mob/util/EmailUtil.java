package br.com.edu_mob.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.edu_mob.dao.impl.FuncionalidadeDAOImpl;
import br.com.edu_mob.entity.Usuario;

public class EmailUtil {
	
	private static final Logger logger = Logger.getLogger(FuncionalidadeDAOImpl.class.getName());

	public static void enviarEmail(String de, String para, String assunto, String mensagem) {
	      try{
	    	 MimeMessage message = new MimeMessage(getSession());
	         message.setFrom(new InternetAddress(de));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(para));
	         message.setSubject(assunto);
	         //message.setText(mensagem);
	         message.setContent(mensagem,"text/html; charset=utf-8");
	         Transport.send(message);
	      }catch (MessagingException mex) {
	    	  logger.log(Level.SEVERE, mex.getMessage(), mex);
	      }
	}
	
	private static Session getSession() {
		javax.mail.Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("systemedumobi@gmail.com", "edumobi100");
			}
		};
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", "systemedumobi@gmail.com");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		return Session.getInstance(properties, authenticator);
	}	
	
	public static String mensagemEnvioSenha(Usuario usuario){
		@SuppressWarnings("unused")
		String html="";
	
		html+="Olá "+usuario.getNome()+", ";
		html+="<br/>";
		html+="<p>Seja bem vindo ao EduMobi, aqui você podem estudar, aprender novas tecnologias e aprimorar seus conhecimentos. Tudo isto com total liberdade de horário e ritmo de estudo.</p>";
		html+="<br/>";
		html+="Sua senha de acesso: "+usuario.getSenha();
		html+="<br/>";
		html+="<h3><img src=\"http://oi57.tinypic.com/714lko.jpg\\>\"</h3>";
		
		return MensagemUtil.getMensagem("info_email_html_senha", usuario.getNome(),usuario.getSenha());
	}
}
