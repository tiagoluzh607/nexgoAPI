package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Usuario;


public class UsuarioDAO extends DAO{

	public UsuarioDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Persiste um Objeto passado como parâmetro no banco de dados
	 * @param usuario
	 */
	public Usuario Insert(Usuario usuario) {
		em.persist(usuario);
		return usuario;
	}

	/**
	 * Persiste ou atualiza um objeto passado como parâmetro no banco de dados
	 * @param usuario
	 */
	public Usuario InsertOrUpdate(Usuario usuario) { 
		usuario = em.merge(usuario);
		em.persist(usuario);
		return usuario;
	}
	
	/**
	 * Deleta um Objeto passado como parâmetro no banco de dados
	 * OBS: Este Objeto deve ter o ID informado
	 * @param usuario
	 */
	public void Delete(Usuario usuario) {
		usuario = em.merge(usuario);
		em.remove(usuario);
	}

	@Override
	public List<Usuario> lista(){
		String jpql = "select obj from "+ Usuario.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<Usuario> usuarios;
		try {
			usuarios = (List<Usuario>) query.getResultList();
		}catch (NoResultException e) {usuarios = new ArrayList<Usuario>();}
		
		return usuarios;
	}
	
	

}
