package br.com.caelum.vraptor.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Aluno extends Pessoa {
	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Deprecated
	public Aluno() {
		super();
	}

	public Aluno(String nome, String rg) {
		this();
		super.nome = nome;
		super.rg = rg;
	}
	
	
	
	
}
