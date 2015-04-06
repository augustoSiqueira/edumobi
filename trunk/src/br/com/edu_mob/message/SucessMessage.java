package br.com.edu_mob.message;

public enum SucessMessage {

	SUCESSO("app_titulo_sucesso"), CADASTRADO_SUCESSO("info_cadastro_sucesso"), ATUALIZADO_SUCESSO(
			"info_atualizado_sucesso"), EXCLUIDO_SUCESSO("info_excluido_sucesso"), CADASTRADA_SUCESSO("info_cadastrada_sucesso"), EXCLUIDA_SUCESSO("info_excluida_sucesso"), ATUALIZADA_SUCESSO("info_atualizada_sucesso")
			,SENHA_REC("senha_rec");

	private SucessMessage(String valor) {
		this.valor = valor;
	}

	private String valor;

	public String getValor() {
		return this.valor;
	}

}
