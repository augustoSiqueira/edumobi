package br.com.edu_mob.entity.enuns;

public enum Sexo {

	MASCULINO(0, "Masculino"), FEMININO(1, "Feminino");

	private Sexo(int valor, String label) {
		this.valor = valor;
		this.label = label;
	}

	private int valor;

	private String label;

	public int getValor() {
		return this.valor;
	}

	public String getLabel() {
		return this.label;
	}

}
