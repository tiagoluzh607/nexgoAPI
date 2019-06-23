package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.model.CaronaMotorista;

public class CaronaMotoristaDAO extends DAO {

	public CaronaMotoristaDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CaronaMotorista> lista(){
		String jpql = "select obj from "+ CaronaMotorista.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<CaronaMotorista> caronaMotoristas;
		try {
			caronaMotoristas = (List<CaronaMotorista>) query.getResultList();
		}catch (NoResultException e) {caronaMotoristas = new ArrayList<CaronaMotorista>();}
		
		return caronaMotoristas;
	}

}
