package br.com.caelum.vraptor.controller;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.dao.PokemonDAO;
import br.com.caelum.vraptor.model.Pokemon;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class PokemonController {
	
	@Inject Result result;
	
	@Get("/pokemons")
	public void exibePokemon() {
		
		Pokemon primeiroPokemon = new Pokemon("Blastoise",3);
		primeiroPokemon.setTipo("Agua");
		primeiroPokemon.setFraqueza("Eletrico");
		
		result.use(Results.json()).from(primeiroPokemon).serialize();
	}
	
	@Get("/salvaPokemon")
	public void salvaPokemon() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//criar Objeto pokemon		
			Pokemon primeiroPokemon = new Pokemon("Blastoise",3);
			primeiroPokemon.setTipo("Agua");
			primeiroPokemon.setFraqueza("Eletrico");
			
			//salvar pokemon no banco		
			em.getTransaction().begin();
				
				List<Pokemon> pokemons =  new PokemonDAO(em).lista();
				new PokemonDAO(em).Insert(primeiroPokemon);
			
			em.getTransaction().commit();
			em.close();
		
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
		
	}
}
