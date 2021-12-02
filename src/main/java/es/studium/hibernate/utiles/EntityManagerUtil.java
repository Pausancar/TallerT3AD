package es.studium.hibernate.utiles;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class EntityManagerUtil {
	public static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestor");//este nombre es el que está en persistence.xml como unidad de persistencia
		//tbn coincide con el nombre de la bd pero no tendría por qué
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	public static void main(String[] args) {
		EntityManager manager = EntityManagerUtil.getEntityManager();
		System.out.println("EntityManager class ==> " + manager.getClass().getCanonicalName());
	}
}

