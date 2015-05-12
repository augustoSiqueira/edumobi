package br.com.edu_mob.message;

public enum InfoMessage {

	TEMPO_ESGOTADO("resposta_simulado_label_tempo_esgotado");

	private String chave;

	private InfoMessage(String chave) {
		this.chave = chave;
	}

	public String getChave() {
		return this.chave;
	}

}
