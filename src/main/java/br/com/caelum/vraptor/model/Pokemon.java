package br.com.caelum.vraptor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pokemon extends Model {
	
	private String nome;
	private String tipo;
	private String fraqueza;
	private int evolucao;
	
	@Deprecated
	public Pokemon() {
	}
	
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
