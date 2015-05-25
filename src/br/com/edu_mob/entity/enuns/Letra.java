package br.com.edu_mob.entity.enuns;

public enum Letra {
	
	A("A"),B("B"),C("C"),D("D"),E("E"),F("F"),G("G"),H("H"),I("I"),J("J"),K("K"),L("L"),M("M");
	
	
	private Letra(String valor) {
		this.valor = valor;
		
	}

	private String valor;	

	public String getValor() {
		return this.valor;
	}

	

}
