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
import br.com.caelum.vraptor.dao.CaronaMotoristaDAO;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.model.CaronaMotorista;
import br.com.caelum.vraptor.model.NexResposta;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class CaronaMotoristaController {
	
	@Inject Result result;
	
	@Get("/caronamotorista")
	public void buscaCaronaMotorista() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<CaronaMotorista> caronamotoristas = new ArrayList<CaronaMotorista>();	
			
			caronamotoristas = new CaronaMotoristaDAO(em).lista();	
			
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronamotoristas);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	@Post("/caronamotorista")
	@Consumes("application/json")
	public void adicionaCaronaMotorista(CaronaMotorista caronaMotorista) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacoes
			if(caronaMotorista.getID() != null) {
				result.use(Results.http()).sendError(400, "O caronaMotorista não pode ter ID, se quiser atualizar use o method PUT");
				return;
			}else if(caronaMotorista.getMotorista().getID() == null) {
				result.use(Results.http()).sendError(400, "O caronaMotorista passado deve ter o Motorista preenchIDo Ex: {Motorista: {}}");
				return;
			}
			
			
			//inseri no banco e busca objeto persistIDo
			
			em.getTransaction().begin();
			
			Usuario motorista = new UsuarioDAO(em).InsertOrUpdate(caronaMotorista.getMotorista());
			caronaMotorista.setMotorista(motorista);
			CaronaMotorista caronaMotoristaDoBanco = (CaronaMotorista) new CaronaMotoristaDAO(em).Insert(caronaMotorista);
			
			em.getTransaction().commit();
			em.close();
			
			NexResposta nexResposta = new NexResposta();
			nexResposta.setObjeto(caronaMotoristaDoBanco);
			
			result.use(Results.json()).withoutRoot().from(nexResposta)
				.recursive()
				.serialize();
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Put("/caronamotorista/{caronaMotorista.ID}")
	@Consumes("application/json")
	public void atualizaCaronaMotorista(CaronaMotorista caronaMotorista) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacoes
			if(caronaMotorista.getID() == null) {
				result.use(Results.http()).sendError(400, "O caronaMotorista deve ter ID para ser atualizado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			//inseri no banco e busca objeto persistIDo
			CaronaMotoristaDAO caronaMotoristaDAO = new CaronaMotoristaDAO(em);
			
			CaronaMotorista caronaMotoristaDoBanco = (CaronaMotorista) caronaMotoristaDAO.SelectPorId(caronaMotorista);
			if(caronaMotoristaDoBanco != null) {
				
				
				em.getTransaction().begin();
				
					caronaMotoristaDAO.InsertOrUpdate(caronaMotorista);
				
				em.getTransaction().commit();
				em.close();
				
				NexResposta nexResposta = new NexResposta();
				nexResposta.setObjeto(caronaMotoristaDoBanco);
				
				result.use(Results.json()).withoutRoot().from(nexResposta)
					.recursive()
					.serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum caronaMotorista no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Delete("/caronamotorista/{caronaMotorista.ID}")
	public void deletaCaronaMotorista(CaronaMotorista caronaMotorista){
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//valIDacao
			if(caronaMotorista.getID() == null) {
				result.use(Results.http()).sendError(400, "O caronaMotorista deve ter ID para ser deletado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			CaronaMotoristaDAO caronaMotoristaDAO = new CaronaMotoristaDAO(em);
			
			CaronaMotorista caronaMotoristaDoBanco = (CaronaMotorista) caronaMotoristaDAO.SelectPorId(caronaMotorista);
			if(caronaMotoristaDoBanco != null) { //verifica se existe caronaMotorista com aquele ID
				
				
				em.getTransaction().begin();
				
					caronaMotoristaDAO.Delete(caronaMotoristaDoBanco);
				
				em.getTransaction().commit();
				em.close();
				
				NexResposta nexResposta = new NexResposta();
				nexResposta.setObjeto(caronaMotoristaDoBanco);
				
				result.use(Results.json()).withoutRoot().from(nexResposta)
					.recursive()
					.serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum caronaMotorista no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	
	

}
