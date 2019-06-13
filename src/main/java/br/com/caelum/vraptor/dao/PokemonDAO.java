package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Model;
import br.com.caelum.vraptor.model.Pokemon;

public class PokemonDAO extends DAO {

	public PokemonDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}


	@Override
	public List<Pokemon> lista(){
		String jpql = "select obj from "+ Pokemon.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<Pokemon> pokemons;
		try {
			pokemons = (List<Pokemon>) query.getResultList();
		}catch (NoResultException e) {pokemons = new ArrayList<Pokemon>();}
		
		return pokemons;
	}


}
