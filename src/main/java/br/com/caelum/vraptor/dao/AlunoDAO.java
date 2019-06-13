package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Aluno;

public class AlunoDAO extends DAO {

	public AlunoDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Aluno> lista() {
		String jpql = "select obj from "+ Aluno.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<Aluno> alunos;
		try {
			alunos = (List<Aluno>) query.getResultList();
		}catch (NoResultException e) {alunos = new ArrayList<Aluno>();}
		
		return alunos;
		
	}

	
}
