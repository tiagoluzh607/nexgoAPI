package br.com.caelum.vraptor.model;

public class Professor extends Pessoa {
	private EEscolaridade escolaridade;

	public EEscolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(EEscolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
}
