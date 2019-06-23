package br.com.caelum.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.service.EntityManagerService;
import br.com.caelum.vraptor.view.Results;

@Controller
public class IndexController {

	@Inject Result result;
	
	public IndexController() {
		
	}
	
	public void home() {
		
	}
	
	@Get("/usuarios")
	public void buscaUsuarios() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
	
			//busca do Banco
			
			
			usuarios = new UsuarioDAO(em).lista();	
			result.use(Results.json()).withoutRoot().from(usuarios).serialize();
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
	}
}
