package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.Carona;
import br.com.caelum.vraptor.model.Model;

public class CaronaDAO extends DAO {

	public CaronaDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<Carona> lista(){
		String jpql = "select obj from "+ Carona.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<Carona> caronas;
		try {
			caronas = (List<Carona>) query.getResultList();
		}catch (NoResultException e) {caronas = new ArrayList<Carona>();}
		
		return caronas;
	}

}
