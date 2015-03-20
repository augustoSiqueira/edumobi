package br.com.edu_mob.message;

public enum Entidades {

	USUARIO("Usuario"), PERFIL("Perfil"), CATEGORIA("Categoria"), ALUNO("Aluno");

	private Entidades(String valor) {
		this.valor = valor;
	}

	private String valor;

	public String getValor() {
		return this.valor;
	}

}
