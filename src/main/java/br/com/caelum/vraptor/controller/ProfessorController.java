package br.com.caelum.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.model.Professor;
import br.com.caelum.vraptor.model.EEscolaridade;
import br.com.caelum.vraptor.model.ESexo;
import br.com.caelum.vraptor.view.Results;

@Controller
public class ProfessorController {
	
	private List<Professor> professores = new ArrayList<Professor>();
	
	
	public ProfessorController() {
		
		
		
		Professor professor1 = new Professor();
		professor1.setId(1);
		professor1.setNome("Mestre dos Magos");
		professor1.setEscolaridade(EEscolaridade.DOUTOR);
		professor1.setSexo(ESexo.MASCULINO);
		professor1.setEmail("mestre_dosmagos@gmail.com");
		professor1.setTelefone("(51) 995847536");
		
		Professor professor2 = new Professor();
		professor2.setId(2);
		professor2.setNome("Mestre Miaji");
		professor2.setEscolaridade(EEscolaridade.MESTRE);
		professor2.setSexo(ESexo.MASCULINO);
		professor2.setEmail("pequeno_gafanhotog@gmail.com");
		professor2.setTelefone("(51) 985472563");
		
		Professor professor3 = new Professor();
		professor3.setId(3);
		professor3.setNome("Mestre Splinter");
		professor3.setEscolaridade(EEscolaridade.MESTRE);
		professor3.setSexo(ESexo.MASCULINO);
		professor3.setEmail("tartarugas@gmail.com");
		professor3.setTelefone("(51) 985632548");
		
		professores.add(professor1);
		professores.add(professor2);
		professores.add(professor3);
	}
	
	@Inject Result result;

	@Get("/professores")
	public void buscaProfessores() {
		
		result.use(Results.json()).withoutRoot().from(professores).serialize();
	}
	
	@Get("/professores/{professor.id}")
	public void buscaProfessor(Professor professor) {
		
		if(professor.getId() < 1) {
			result.use(Results.http()).sendError(400, "Não foi passado uma o id do professor ");
		}
		
		for (Professor professorDoBanco : professores) {
			if(professor.getId() == professorDoBanco.getId()) {
				
				result.use(Results.json()).withoutRoot().from(professorDoBanco).serialize();
				return;
			}
		}
		
	}
	
	@Post("/professores")
	@Consumes("application/json")
	public void adicionaProfessor(Professor professor) {
		
		//Adicionar Professor no Banco pegar ID e devolver professor com id
		professor.setId(4);
		
		result.use(Results.json()).from(professor).serialize();
		
	}
}
