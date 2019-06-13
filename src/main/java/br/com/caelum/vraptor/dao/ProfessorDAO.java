package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Professor;

public class ProfessorDAO extends DAO {

	public ProfessorDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<Professor> lista() {
		String jpql = "select obj from "+ Professor.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<Professor> professors;
		try {
			professors = (List<Professor>) query.getResultList();
		}catch (NoResultException e) {professors = new ArrayList<Professor>();}
		
		return professors;
	}
	
	

}
