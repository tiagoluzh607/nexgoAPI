package br.com.caelum.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.model.Aluno;
import br.com.caelum.vraptor.model.ESexo;
import br.com.caelum.vraptor.view.Results;

@Controller
public class AlunoController {
	
	
	@Inject Result result;

	@Get("/alunos")
	public void buscaAlunos() {
		
		List<Aluno> alunos = new ArrayList<Aluno>();

		Aluno aluno1 = new Aluno("Samuel", "51254251");
		Aluno aluno2 = new Aluno("Denner", "541258745");

		alunos.add(aluno1);
		alunos.add(aluno2);
		
		result.use(Results.json()).withoutRoot().from(alunos).serialize();
	}
	
	
	@Post("/alunos")
	@Consumes("application/json")
	public void adicionaAluno(Aluno aluno) {
		
		//Adicionar Aluno no Banco pegar ID e devolver aluno com id
		aluno.setId(4);
		
		result.use(Results.json()).from(aluno).serialize();
		
	}
}
