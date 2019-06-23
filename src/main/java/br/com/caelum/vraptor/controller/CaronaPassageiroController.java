package br.com.caelum.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.CaronaPassageiroDAO;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.model.CaronaPassageiro;
import br.com.caelum.vraptor.model.EStatusPassageiro;
import br.com.caelum.vraptor.model.NexResposta;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class CaronaPassageiroController {
	@Inject Result result;
	
	@Get("/caronapassageiro")
	public void buscaCaronaPassageiro() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<CaronaPassageiro> caronapassageiros = new ArrayList<CaronaPassageiro>();	
			
			caronapassageiros = new CaronaPassageiroDAO(em).lista();	
			
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronapassageiros);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	@Post("/caronapassageiro")
	@Consumes("application/json")
	public void adicionaCaronaPassageiro(CaronaPassageiro caronaPassageiro) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacoes
			if(caronaPassageiro.getID() != null) {
				result.use(Results.http()).sendError(400, "O caronaPassageiro não pode ter ID, se quiser atualizar use o method PUT");
				return;
			}else if(caronaPassageiro.getPassageiro().getID() == null) {
				result.use(Results.http()).sendError(400, "O caronaPassageiro passado deve ter o Passageiro preenchIDo Ex: {Passageiro: {}}");
				return;
			}
			
			
			//inseri no banco e busca objeto persistIDo
			
			em.getTransaction().begin();
			
			Usuario passageiro = new UsuarioDAO(em).InsertOrUpdate(caronaPassageiro.getPassageiro());
			caronaPassageiro.setPassageiro(passageiro);
			CaronaPassageiro caronaPassageiroDoBanco = (CaronaPassageiro) new CaronaPassageiroDAO(em).Insert(caronaPassageiro);
			
			em.getTransaction().commit();
			em.close();
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronaPassageiroDoBanco);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Put("/caronapassageiro/{caronaPassageiro.ID}")
	@Consumes("application/json")
	public void atualizaCaronaPassageiro(CaronaPassageiro caronaPassageiro) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacoes
			if(caronaPassageiro.getID() == null) {
				result.use(Results.http()).sendError(400, "O caronaPassageiro deve ter ID para ser atualizado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			//inseri no banco e busca objeto persistIDo
			CaronaPassageiroDAO caronaPassageiroDAO = new CaronaPassageiroDAO(em);
			
			CaronaPassageiro caronaPassageiroDoBanco = (CaronaPassageiro) caronaPassageiroDAO.SelectPorId(caronaPassageiro);
			if(caronaPassageiroDoBanco != null) {
				
				
				em.getTransaction().begin();
				
					caronaPassageiroDAO.InsertOrUpdate(caronaPassageiro);
				
				em.getTransaction().commit();
				em.close();
				
				NexResposta nexResposta = new NexResposta();
				nexResposta.setObjeto(caronaPassageiroDoBanco);
				
				result.use(Results.json()).withoutRoot().from(nexResposta)
					.recursive()
					.serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum caronaPassageiro no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Delete("/caronapassageiro/{caronaPassageiro.ID}")
	public void deletaCaronaPassageiro(CaronaPassageiro caronaPassageiro){
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacao
			if(caronaPassageiro.getID() == null) {
				result.use(Results.http()).sendError(400, "O caronaPassageiro deve ter ID para ser deletado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			CaronaPassageiroDAO caronaPassageiroDAO = new CaronaPassageiroDAO(em);
			
			CaronaPassageiro caronaPassageiroDoBanco = (CaronaPassageiro) caronaPassageiroDAO.SelectPorId(caronaPassageiro);
			if(caronaPassageiroDoBanco != null) { //verifica se existe caronaPassageiro com aquele ID
				
				
				em.getTransaction().begin();
				
					caronaPassageiroDAO.Delete(caronaPassageiroDoBanco);
				
				em.getTransaction().commit();
				em.close();
				
				NexResposta nexResposta = new NexResposta();
				nexResposta.setObjeto(caronaPassageiroDoBanco);
				
				result.use(Results.json()).withoutRoot().from(nexResposta)
					.recursive()
					.serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum caronaPassageiro no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	/*#################### recursos especiais########################*/
	
	@Get("/caronapassageiro/buscadestinoporgeolocalizacao")
	public void buscaCaronasPassageiroComMesmaLatELongFim(Double latfim, Double longfim) {
		
		if(latfim == null || longfim == null) {
			result.use(Results.http()).sendError(400, "Você deve passar como parametro a latfim e a longfim que deseja pesquisar exemplo: url/caronapassageiro/buscadestinoporgeolocalizacao?latfim=$&longfim=$");
			return;
		}
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<CaronaPassageiro> caronapassageiros = new ArrayList<CaronaPassageiro>();	
			
			caronapassageiros = new CaronaPassageiroDAO(em).listaDestinoPorGeolocalizacao(latfim, longfim, EStatusPassageiro.PROCURANDO_MOTORISTA);	
			
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronapassageiros);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	
}
