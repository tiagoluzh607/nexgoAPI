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
import br.com.caelum.vraptor.dao.ProfessorDAO;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.model.Professor;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class ProfessorController {
	
	
	@Inject Result result;

	@Get("/professores")
	public void buscaProfessores() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<Professor> professores = new ArrayList<Professor>();
	
			//busca do Banco
			
			professores = new ProfessorDAO(em).lista();
			
			result.use(Results.json()).withoutRoot().from(professores).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	@Get("/professores/{professor.id}")
	public void buscaProfessor(Professor professor) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacao
			if(professor.getId() < 1) {
				result.use(Results.http()).sendError(400, "Você deve passar o ID");
				return;
			}
		
			ProfessorDAO professorDAO = new ProfessorDAO(em);
			
			Professor professorDoBanco = (Professor) professorDAO.SelectPorId(professor);
			
			if(professorDoBanco == null) { //verifica se existe professor com aquele ID
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum professor no banco");
				return;
			}
				
			result.use(Results.json()).from(professorDoBanco).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
				
	}
	
	
	@Post("/professores")
	@Consumes("application/json")
	public void adicionaProfessor(Professor professor) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacoes
			if(professor.getId() > 0) {
				result.use(Results.http()).sendError(400, "O professor não pode ter ID, se quiser atualizar use o method PUT");
				return;
			}else if(professor.getNome() == null || professor.getNome().isEmpty()) {
				result.use(Results.http()).sendError(400, "O professor passado deve ter nome preenchido Ex: {nome: Lucas}");
				return;
			}else if(professor.getRg() == null || professor.getRg().isEmpty()) {
				result.use(Results.http()).sendError(400, "O professor passado deve ter rg preenchido Ex: {rg: 1254155}");
				return;
			}else if(professor.getEscolaridade() == null){
				result.use(Results.http()).sendError(400, "O professor passado deve ter escolaridade Ex: {escolaridade: MESTRE|DOUTOR");
				return;
			}
			
			
			//inseri no banco e busca objeto persistido
			
			em.getTransaction().begin();
			
			Professor professorDoBanco = (Professor) new ProfessorDAO(em).Insert(professor);
			
			em.getTransaction().commit();
			em.close();
			
			result.use(Results.json()).from(professorDoBanco).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	@Put("/professores/{professor.id}")
	@Consumes("application/json")
	public void atualizaProfessor(Professor professor) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacoes
			if(professor.getId() < 1) {
				result.use(Results.http()).sendError(400, "O professor deve ter ID apra ser atualizado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			//inseri no banco e busca objeto persistido
			ProfessorDAO professorDAO = new ProfessorDAO(em);
			
			Professor professorDoBanco = (Professor) professorDAO.SelectPorId(professor);
			if(professorDoBanco != null) {
				
				
				em.getTransaction().begin();
				
					professorDAO.InsertOrUpdate(professor);
				
				em.getTransaction().commit();
				em.close();
				
				result.use(Results.json()).from(professorDoBanco).serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum professor no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Delete("/professores/{professor.id}")
	public void deletaProfessor(Professor professor){
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacao
			if(professor.getId() < 1) {
				result.use(Results.http()).sendError(400, "O professor deve ter ID para ser deletado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			ProfessorDAO professorDAO = new ProfessorDAO(em);
			
			Professor professorDoBanco = (Professor) professorDAO.SelectPorId(professor);
			if(professorDoBanco != null) { //verifica se existe professor com aquele ID
				
				
				em.getTransaction().begin();
				
					professorDAO.Delete(professorDoBanco);
				
				em.getTransaction().commit();
				em.close();
				
				result.use(Results.json()).from(professorDoBanco).serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum professor no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
}
