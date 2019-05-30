package br.com.caelum.vraptor.model;

public class Pokemon {
	
	private String nome;
	private String tipo;
	private String fraqueza;
	private int evolucao;
	
	public Pokemon(String nome, int evolucao) {
		this.nome = nome;
		this.evolucao = evolucao;
	}
	
	public void setTipo(String tipoPokemon) {
		this.tipo = tipoPokemon;
	}
	
	public void setFraqueza(String fraquezaPokemon) {
		this.fraqueza = fraquezaPokemon;
	}

}
