package apinexgo;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.vraptor.dao.CaronaMotoristaDAO;
import br.com.caelum.vraptor.dao.CaronaPassageiroDAO;
import br.com.caelum.vraptor.dao.JPAUtil;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.model.CaronaMotorista;
import br.com.caelum.vraptor.model.CaronaPassageiro;
import br.com.caelum.vraptor.model.EStatusMotorista;
import br.com.caelum.vraptor.model.EStatusPassageiro;
import br.com.caelum.vraptor.model.Usuario;


public class UsuarioDAOTest {

	@Test
	public void aTestaInserirUmBeneficioNoBanco() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		
		Usuario usuario = new Usuario();
		usuario.setNome("Tiago");
		usuario.setID("idstringquenuncaserepete");
		usuario = (Usuario) new UsuarioDAO(em).InsertOrUpdate(usuario);
		
		CaronaMotorista caronaMotorista = new CaronaMotorista();
		caronaMotorista.setStatus(EStatusMotorista.PROCURANDO_PASSAGEIRO);	
		caronaMotorista.setMotorista(usuario);
		
		CaronaPassageiro caronaPassageiro = new CaronaPassageiro();
		caronaPassageiro.setStatus(EStatusPassageiro.PROCURANDO_MOTORISTA);
		caronaPassageiro.setPassageiro(usuario);
		
		
		new CaronaMotoristaDAO(em).InsertOrUpdate(caronaMotorista);
		new CaronaPassageiroDAO(em).InsertOrUpdate(caronaPassageiro);
		
		List<CaronaMotorista> motoristas = new CaronaMotoristaDAO(em).lista();
		
		em.getTransaction().commit();	
		em.close();
		
		Assert.assertTrue(true);
	}
}
