package br.com.caelum.vraptor.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe Responsável por fornecer um entity manager para conexão com banco de dados
 * Esta classe utiliza a mesma instancia de Entity manager pois assim é possível controlar um pool de conexões com o banco de dados
 * 
 * @author Tiago Luz
 *
 */
public class JPAUtil {

	//private static final EntityManagerFactory emFabrica = Persistence.createEntityManagerFactory("jpa-postgres");
	private static final EntityManagerFactory emFabrica = Persistence.createEntityManagerFactory("jpa-postgres-teste");
	
	

	/**
	 * Retorna um Entity Manager de Conexao com o banco de dados
	 * @return
	 */
	public EntityManager getEntityManager() {
		return emFabrica.createEntityManager();
	}
	
}
