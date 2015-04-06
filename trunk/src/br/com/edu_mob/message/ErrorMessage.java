package br.com.edu_mob.message;

public enum ErrorMessage {

	ERRO("app_titulo_erro"),
	DAO("erro_dao"),
	CAMPO_OBRIGATORIO("campo_obrigatorio"),
	LOGIN_USUARIO_NAO_ENCONTRADO("erro_usuario_nao_encontrado"),
	LOGIN_SENHA_INVALIDOS("erro_login_senha_invalidos"),
	PERFIL_NOME_EXISTENTE("erro_perfil_nome_existente"),
	PERFIL_FUNCIONALIDADE_OBRIGATORIA("erro_perfil_funcionalidade_obrigatoria"),
	USUARIO_EMAIL_EXISTENTE("erro_usuario_email_existente"),
	USUARIO_CPF_EXISTENTE("erro_usuario_cpf_existente"),
	USUARIO_CPF_INVALIDO("erro_usuario_cpf_invalido"),
	USUARIO_EMAIL_INVALIDO("erro_usuario_email_invalido"),
	USUARIO_CPF_VAZIO("erro_usuario_cpf_vazio"),
	USUARIO_NOME_VAZIO("erro_usuario_nome_vazio"),
	USUARIO_EMAIL_VAZIO("erro_usuario_email_vazio"),
	USUARIO_LOGADO_EXCLUIR("erro_usuario_nao_pode_excluir"),
	CATEGORIA_NOME_EXISTENTE("erro_categoria_nome_existente"),
	AREA_CONHECIMENTO_DESC_EXISTENTE("erro_area_conhecimento_desc_existente"),
	DEPENDENCIA_EXISTENTE("erro_dependencia_entidade"),
	DADOS_INVALIDOS("erro_dados_invalidos"),
	RECUPERAR_SENHA_EMAIL_INVALIDO("erro_email_n_corresponde"),
	ALUNO_DATA_NASCIMENTO_MAIOR_DATA_ATUAL("erro_aluno_data_nascimento_invalida"),
	ALTERNATIVAS_INVALIDAS("erro_alternativa_invalida"),
	ALTERNATIVA_CORRETA_MAIOR("erro_alternativa_correta_maior"),
	ALTERNATIVA_CORRETA_MENOR("erro_alternativa_correta_menor"),
	ARQUIVO_NAO_ENCONTRADO("erro_arquivo_nao_encontrado"),
	ARQUIVO_TIPO_NAO_SUPORTADO("erro_arquivo_tipo_nao_suportado"),
	ARQUIVO_TAMANHO_LIMITE("erro_arquivo_tamanho_limite"),
	CAMPO_NOME_VAZIO("erro_campo_nome_vazio"),
	CAMPO_TITULO_VAZIO("erro_campo_titulo_vazio"),
	CAMPO_DESCRICAO_VAZIO("erro_campo_descricao_vazio"),
	CAMPO_QNT_QUESTAO_VAZIO("erro_campo_qnt_questao_vazio"), DADOS_PESSOAIS_SENHA_NAO_CONFERE("erro_dados_pessoais_senha_nao_confere");

	private ErrorMessage(String chave) {
		this.chave = chave;
	}

	private String chave;

	public String getChave() {
		return this.chave;
	}

}
