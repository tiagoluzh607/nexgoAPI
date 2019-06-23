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
import br.com.caelum.vraptor.dao.CaronaDAO;
import br.com.caelum.vraptor.dao.CaronaMotoristaDAO;
import br.com.caelum.vraptor.dao.CaronaPassageiroDAO;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.model.Carona;
import br.com.caelum.vraptor.model.CaronaMotorista;
import br.com.caelum.vraptor.model.CaronaPassageiro;
import br.com.caelum.vraptor.model.EStatusCarona;
import br.com.caelum.vraptor.model.EStatusPassageiro;
import br.com.caelum.vraptor.model.NexResposta;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class CaronaController {
	
	@Inject Result result;
	
	@Get("/carona")
	public void buscaCarona() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<Carona> caronas = new ArrayList<Carona>();	
			
			caronas = new CaronaDAO(em).lista();	
			
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronas);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	@Post("/carona")
	@Consumes("application/json")
	public void adicionaCarona(Carona carona) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacoes
			if(carona.getID() != null) {
				result.use(Results.http()).sendError(400, "O carona não pode ter ID, se quiser atualizar use o method PUT");
				return;
			}else if(carona.getMotorista().getID() == null) {
				result.use(Results.http()).sendError(400, "O carona passado deve ter o Motorista  preenchido Ex: { Motorista: {}}");
				return;
			}else if(carona.getPassageiros() == null || carona.getPassageiros().isEmpty()) {
				result.use(Results.http()).sendError(400, "O carona passado deve ter o Pelo menos um passageiro na lista de passageiros preenchido Ex: {Passageiros: [{},  ]}");
				return;
			}
			
			
			em.getTransaction().begin();
			
			CaronaMotorista caronaMotorista  = (CaronaMotorista) new CaronaMotoristaDAO(em).InsertOrUpdate(carona.getMotorista());
			carona.setMotorista(caronaMotorista);
			carona.setStatus(EStatusCarona.COM_VAGAS);
			Carona caronaDoBanco = (Carona) new CaronaDAO(em).Insert(carona);
			
			em.getTransaction().commit();
			
			//inseri no banco e busca objeto persistIDo
			
			
			CaronaPassageiroDAO caronaPassageiroDao = new CaronaPassageiroDAO(em);
			
			for (CaronaPassageiro caronaPassageiro : carona.getPassageiros()) {
				em.getTransaction().begin();
				
				caronaPassageiro.setCarona(caronaDoBanco);
				caronaPassageiroDao.InsertOrUpdate(caronaPassageiro);
				
				em.getTransaction().commit();
			}
			
			
			em.close();
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronaDoBanco);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Put("/carona/{carona.ID}")
	@Consumes("application/json")
	public void atualizaCarona(Carona carona) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacoes
			if(carona.getID() == null) {
				result.use(Results.http()).sendError(400, "O carona deve ter ID para ser atualizado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			//inseri no banco e busca objeto persistIDo
			CaronaDAO caronaDAO = new CaronaDAO(em);
			
			Carona caronaDoBanco = (Carona) caronaDAO.SelectPorId(carona);
			if(caronaDoBanco != null) {
				
				
				em.getTransaction().begin();
				
					caronaDAO.InsertOrUpdate(carona);
				
				em.getTransaction().commit();
				em.close();
				
				NexResposta nexResposta = new NexResposta();
				nexResposta.setObjeto(caronaDoBanco);
				
				result.use(Results.json()).withoutRoot().from(nexResposta)
					.recursive()
					.serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum carona no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Delete("/carona/{carona.ID}")
	public void deletaCarona(Carona carona){
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacao
			if(carona.getID() == null) {
				result.use(Results.http()).sendError(400, "O carona deve ter ID para ser deletado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			CaronaDAO caronaDAO = new CaronaDAO(em);
			
			Carona caronaDoBanco = (Carona) caronaDAO.SelectPorId(carona);
			if(caronaDoBanco != null) { //verifica se existe carona com aquele ID
				
				
				em.getTransaction().begin();
				
					caronaDAO.Delete(caronaDoBanco);
				
				em.getTransaction().commit();
				em.close();
				
				NexResposta nexResposta = new NexResposta();
				nexResposta.setObjeto(caronaDoBanco);
				
				result.use(Results.json()).withoutRoot().from(nexResposta)
					.recursive()
					.serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum carona no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	
	@Post("/carona/adicionaPassageiro")
	@Consumes("application/json")
	public void adicionaPassageiro(Carona carona, CaronaPassageiro caronaPassageiro) {
		
		
		if(carona == null || caronaPassageiro == null) {
			result.use(Results.http()).sendError(400, "Você deve passar como parametro um objeto carona e caronaPassageiro com id ex: {carona: {ID: AquiSuaStringId}, caronaPassageiro: {ID: AquiSuaStringId}}");
			return;
		}
		
		if(carona.getID() == null || carona.getID().isEmpty()) {
			result.use(Results.http()).sendError(400, "Você deve passar como parametro um objeto carona com id ex: {carona: {ID: AquiSuaStringId}}");
			return;
		}
		
		if(caronaPassageiro.getID() == null || caronaPassageiro.getID().isEmpty()) {
			result.use(Results.http()).sendError(400, "Você deve passar como parametro um objeto caronaPassageiro com id ex: {caronaPassageiro: {ID: AquiSuaStringId}}");
			return;
		}
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			
				CaronaDAO caronaDAO = new CaronaDAO(em);
				
				Carona caronaDoBanco = (Carona) caronaDAO.SelectPorId(carona);
				if(caronaDoBanco != null) { //verifica se existe carona com aquele ID
					
					
					CaronaPassageiroDAO caronaPassageiroDAO = new CaronaPassageiroDAO(em);
					
					CaronaPassageiro caronaPassageiroDoBanco = (CaronaPassageiro) caronaPassageiroDAO.SelectPorId(caronaPassageiro);
					if(caronaPassageiroDoBanco != null) { //verifica se existe caronaPassageiro com aquele ID
					
						em.getTransaction().begin();
						
							caronaPassageiroDoBanco.setCarona(caronaDoBanco);
							caronaPassageiroDAO.InsertOrUpdate(caronaDoBanco);
						
						em.getTransaction().commit();
						em.close();
						
						NexResposta nexResposta = new NexResposta();
						nexResposta.setObjeto(caronaDoBanco);
						
						result.use(Results.json()).withoutRoot().from(nexResposta)
							.recursive()
							.serialize();
					
					}else {
						result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum carona no banco");
						return;
					}
					
					
					
				}else {
					result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum carona no banco");
					return;
				}
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	
}
