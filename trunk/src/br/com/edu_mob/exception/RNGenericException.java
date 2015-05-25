package br.com.edu_mob.exception;

import java.util.List;

public class RNGenericException extends RNException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RNGenericException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RNGenericException(List<String> listaMsg) {
		super(listaMsg);
		// TODO Auto-generated constructor stub
	}

	public RNGenericException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	
}
