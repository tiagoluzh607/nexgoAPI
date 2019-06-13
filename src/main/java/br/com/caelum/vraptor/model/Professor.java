package br.com.caelum.vraptor.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Professor extends Pessoa {
	
	@Enumerated(EnumType.STRING)
	private EEscolaridade escolaridade;
	
	@Deprecated
	public Professor(){}
	
	public Professor(String nome, String rg, EEscolaridade escolaridade) {
		this();
		super.nome = nome;
		super.rg = rg;
		this.escolaridade = escolaridade;
	}

	public EEscolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(EEscolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
}
