package br.com.edu_mob.exception;

import java.util.ArrayList;
import java.util.List;

public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> listaMensagens = new ArrayList<String>();

	public GenericException() {
		super();
	}

	public GenericException(String msg) {
		this.listaMensagens.add(msg);
	}

	public List<String> getListaMensagens() {
		return this.listaMensagens;
	}

}
