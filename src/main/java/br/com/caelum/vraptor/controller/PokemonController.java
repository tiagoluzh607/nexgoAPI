package br.com.caelum.vraptor.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.model.Pokemon;
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
}
