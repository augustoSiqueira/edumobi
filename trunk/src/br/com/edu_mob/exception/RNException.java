package br.com.edu_mob.exception;

import java.util.List;

public class RNException extends GenericException {

	private static final long serialVersionUID = 1L;

	public RNException() {
		super();
	}

	public RNException(String msg) {
		super(msg);
	}

	public RNException(List<String> listaMsg) {
		super(listaMsg);
	}

}
