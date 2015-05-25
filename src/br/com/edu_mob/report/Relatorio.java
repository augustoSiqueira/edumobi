package br.com.edu_mob.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public abstract class Relatorio {

	private static Logger logger = Logger.getLogger(Relatorio.class.getName());

	protected List<?> lista;
	protected Map parametros;
	protected InputStream caminho;

	public InputStream imprimirPDF() {
		JasperPrint jasperPrint = null;
		JRBeanCollectionDataSource listaDataSource = new JRBeanCollectionDataSource(this.lista);
		try {
			jasperPrint = JasperFillManager.fillReport(this.caminho, this.parametros, listaDataSource);
		} catch(JRException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
		JRPdfExporter jrPdfExporter = new JRPdfExporter();
		jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		jrPdfExporter.setConfiguration(configuration);

		try {
			jrPdfExporter.exportReport();
		} catch(JRException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public List<?> getLista() {
		return this.lista;
	}

	public void setLista(List<?> lista) {
		this.lista = lista;
	}

	public Map getParametros() {
		return this.parametros;
	}

	public void setParametros(Map parametros) {
		this.parametros = parametros;
	}

	public InputStream getCaminho() {
		return this.caminho;
	}

	public void setCaminho(InputStream caminho) {
		this.caminho = caminho;
	}

}
