package br.com.edu_mob.message;

public enum ErrorMessage {

	ERRO("app_titulo_erro"), DAO("erro_dao"), CAMPO_OBRIGATORIO("campo_obrigatorio"), LOGIN_USUARIO_NAO_ENCONTRADO(
			"erro_usuario_nao_encontrado"), LOGIN_SENHA_INVALIDOS("erro_login_senha_invalidos"), PERFIL_NOME_EXISTENTE(
			"erro_perfil_nome_existente"), PERFIL_FUNCIONALIDADE_OBRIGATORIA("erro_perfil_funcionalidade_obrigatoria"), USUARIO_EMAIL_EXISTENTE(
			"erro_usuario_email_existente"), USUARIO_CPF_EXISTENTE("erro_usuario_cpf_existente"), USUARIO_CPF_INVALIDO(
			"erro_usuario_cpf_invalido"), USUARIO_EMAIL_INVALIDO("erro_usuario_email_invalido");

	private ErrorMessage(String chave) {
		this.chave = chave;
	}

	private String chave;

	public String getChave() {
		return this.chave;
	}

}
