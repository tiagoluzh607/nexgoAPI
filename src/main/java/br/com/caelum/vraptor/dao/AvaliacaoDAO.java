package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Avaliacao;
import br.com.caelum.vraptor.model.Avaliacao;
import br.com.caelum.vraptor.model.Model;

public class AvaliacaoDAO extends DAO {

	public AvaliacaoDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<Avaliacao> lista() {
		String jpql = "select obj from "+ Avaliacao.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<Avaliacao> avaliacoes;
		try {
			avaliacoes = (List<Avaliacao>) query.getResultList();
		}catch (NoResultException e) {avaliacoes = new ArrayList<Avaliacao>();}
		
		return avaliacoes;
	}
	
	
	

}
