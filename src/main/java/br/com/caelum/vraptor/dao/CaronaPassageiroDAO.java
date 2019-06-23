package br.com.caelum.vraptor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.vraptor.model.CaronaPassageiro;
import br.com.caelum.vraptor.model.EStatusPassageiro;
import br.com.caelum.vraptor.model.Model;

public class CaronaPassageiroDAO extends DAO {

	public CaronaPassageiroDAO(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CaronaPassageiro> lista(){
		String jpql = "select obj from "+ CaronaPassageiro.class.getSimpleName() +" as obj";
		Query query = super.em.createQuery(jpql);
		
		List<CaronaPassageiro> caronaPassageiros;
		try {
			caronaPassageiros = (List<CaronaPassageiro>) query.getResultList();
		}catch (NoResultException e) {caronaPassageiros = new ArrayList<CaronaPassageiro>();}
		
		return caronaPassageiros;
	}

	public List<CaronaPassageiro> listaDestinoPorGeolocalizacao(Double latfim, Double longfim,
			EStatusPassageiro statusPassageiro) {
		
		String jpql = "select c "
				+     "from "+ CaronaPassageiro.class.getSimpleName() +" as c "
					+ "where c.LatFim = :pLatFim AND "
						+ "c.LongFim = :pLongFim AND "
						+ "c.Status = :pStatus "
					+ "order by  c.ID desc";
		TypedQuery<CaronaPassageiro> query = super.em.createQuery(jpql, CaronaPassageiro.class);
		query.setParameter("pLatFim", latfim);
		query.setParameter("pLongFim", longfim);
		query.setParameter("pStatus", statusPassageiro);
		
		List<CaronaPassageiro> caronapassageiros;
		try {
			caronapassageiros = (List<CaronaPassageiro>) query.getResultList();
		}catch (NoResultException e) {caronapassageiros = new ArrayList<CaronaPassageiro>();}
		return caronapassageiros;
		
	}

}
