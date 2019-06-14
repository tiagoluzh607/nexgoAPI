package br.com.caelum.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.AlunoDAO;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.model.Aluno;
import br.com.caelum.vraptor.model.ESexo;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class AlunoController {
	
	
	@Inject Result result;

	@Get("/alunos")
	public void buscaAlunos() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<Aluno> alunos = new ArrayList<Aluno>();
	
			//busca do Banco
			
			
			alunos = new AlunoDAO(em).lista();	
			result.use(Results.json()).withoutRoot().from(alunos).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	@Get("/alunos/{aluno.id}")
	public void buscaAluno(Aluno aluno) {
		EntityManager em = new JPAUtil().getEntityManager();
		//validacao
		try {
			if(aluno.getId() < 1) {
				result.use(Results.http()).sendError(400, "Você deve passar o ID");
				return;
			}
			
			AlunoDAO alunoDAO = new AlunoDAO(em);
			
			Aluno alunoDoBanco = (Aluno) alunoDAO.SelectPorId(aluno);
			
			if(alunoDoBanco == null) { //verifica se existe aluno com aquele ID
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum aluno no banco");
				return;
			}
				
			result.use(Results.json()).from(alunoDoBanco).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}	
	}
	
	
	@Post("/alunos")
	@Consumes("application/json")
	public void adicionaAluno(Aluno aluno) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacoes
			if(aluno.getId() > 0) {
				result.use(Results.http()).sendError(400, "O aluno não pode ter ID, se quiser atualizar use o method PUT");
				return;
			}else if(aluno.getNome() == null || aluno.getNome().isEmpty()) {
				result.use(Results.http()).sendError(400, "O aluno passado deve ter nome preenchido Ex: {nome: Lucas}");
				return;
			}else if(aluno.getRg() == null || aluno.getRg().isEmpty()) {
				result.use(Results.http()).sendError(400, "O aluno passado deve ter rg preenchido Ex: {rg: 1254155}");
				return;
			}
			
			
			//inseri no banco e busca objeto persistido
			
			em.getTransaction().begin();
			
			Aluno alunoDoBanco = (Aluno) new AlunoDAO(em).Insert(aluno);
			
			em.getTransaction().commit();
			em.close();
			
			result.use(Results.json()).from(alunoDoBanco).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Put("/alunos/{aluno.id}")
	@Consumes("application/json")
	public void atualizaAluno(Aluno aluno) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacoes
			if(aluno.getId() < 1) {
				result.use(Results.http()).sendError(400, "O aluno deve ter ID apra ser atualizado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			//inseri no banco e busca objeto persistido
			AlunoDAO alunoDAO = new AlunoDAO(em);
			
			Aluno alunoDoBanco = (Aluno) alunoDAO.SelectPorId(aluno);
			if(alunoDoBanco != null) {
				
				
				em.getTransaction().begin();
				
					alunoDAO.InsertOrUpdate(aluno);
				
				em.getTransaction().commit();
				em.close();
				
				result.use(Results.json()).from(alunoDoBanco).serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum aluno no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}
	
	@Delete("/alunos/{aluno.id}")
	public void deletaAluno(Aluno aluno){
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			//validacao
			if(aluno.getId() < 1) {
				result.use(Results.http()).sendError(400, "O aluno deve ter ID para ser deletado, se quiser inserir um novo utilize o POST");
				return;
			}
			
			AlunoDAO alunoDAO = new AlunoDAO(em);
			
			Aluno alunoDoBanco = (Aluno) alunoDAO.SelectPorId(aluno);
			if(alunoDoBanco != null) { //verifica se existe aluno com aquele ID
				
				
				em.getTransaction().begin();
				
					alunoDAO.Delete(alunoDoBanco);
				
				em.getTransaction().commit();
				em.close();
				
				result.use(Results.json()).from(alunoDoBanco).serialize();
				
				
			}else {
				result.use(Results.http()).sendError(400, "Com o ID informado não existe nenhum aluno no banco");
				return;
			}
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
	
	
}
