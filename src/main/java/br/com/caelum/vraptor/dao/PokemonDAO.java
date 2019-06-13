package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import br.com.caelum.vraptor.model.Pokemon;

public class PokemonDAO {

	private EntityManager em;

	public PokemonDAO(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * Persiste um Objeto passado como parâmetro no banco de dados
	 * @param pokemon
	 */
	public Pokemon Insert(Pokemon pokemon) {
		em.persist(pokemon);
		return pokemon;
	}

	/**
	 * Persiste ou atualiza um objeto passado como parâmetro no banco de dados
	 * @param pokemon
	 */
	public Pokemon InsertOrUpdate(Pokemon pokemon) { 
		pokemon = em.merge(pokemon);
		em.persist(pokemon);
		return pokemon;
	}
	
	/**
	 * Deleta um Objeto passado como parâmetro no banco de dados
	 * OBS: Este Objeto deve ter o ID informado
	 * @param pokemon
	 */
	public void Delete(Pokemon pokemon) {
		pokemon = em.merge(pokemon);
		em.remove(pokemon);
	}
	
	/**
	 * Traz um Registro pesquisando por id no banco de dados
	 * @param model
	 * @return
	 */
	public List<Pokemon> lista(){
		String jpql = "select obj from "+ Pokemon.class.getSimpleName() +" as obj";
		Query query = em.createQuery(jpql);
		
		List<Pokemon> pokemons;
		try {
			pokemons = (List<Pokemon>) query.getResultList();
		}catch (NoResultException e) {pokemons = new ArrayList<Pokemon>();}
		
		return pokemons;
	}
}
