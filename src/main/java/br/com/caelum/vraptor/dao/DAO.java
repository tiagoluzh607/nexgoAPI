package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Model;


public abstract class DAO {

	protected EntityManager em;

	public DAO(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * Persiste um Objeto passado como parâmetro no banco de dados
	 * @param model
	 */
	public Model Insert(Model model) {
		em.persist(model);
		return model;
	}

	/**
	 * Persiste ou atualiza um objeto passado como parâmetro no banco de dados
	 * @param model
	 */
	public Model InsertOrUpdate(Model model) { 
		model = em.merge(model);
		em.persist(model);
		return model;
	}
	
	/**
	 * Deleta um Objeto passado como parâmetro no banco de dados
	 * OBS: Este Objeto deve ter o ID informado
	 * @param model
	 */
	public void Delete(Model model) {
		model = em.merge(model);
		em.remove(model);
	}
	
	/**
	 * Traz um Registro pesquisando por id no banco de dados
	 * @param model
	 * @return
	 */
	public Model SelectPorId(Model model) {
		return em.find(  model.getClass(), model.getID());
	}
	
	/**
	 * Você deve Passar uma String com o nome da classe que deseja buscar no banco de dados
	 * @param nomeClass deve det usado Class.class.getSimpleName()
	 * @return
	 */
	public abstract <T extends Model> List<T> lista();
	
	
}
