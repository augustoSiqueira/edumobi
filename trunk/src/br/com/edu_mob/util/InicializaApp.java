package br.com.edu_mob.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InicializaApp implements ServletContextListener{

	public static String CAMINHO_SERVIDOR = "";
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
            CAMINHO_SERVIDOR = arg0.getServletContext().getRealPath("/");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }		
	}

}
