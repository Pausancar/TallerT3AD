package es.studium.hibernate.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.studium.hibernate.utiles.EntityManagerUtil;

//T es un tipo de dato genérico
public abstract class AbstractDao<T> implements Dao<T>
{
	//es privada para que no se pueda acceder a el desde cualquier parte del programa, entonces para trabajar con el tengo que usar los getter y setter
	private EntityManager entityManager = EntityManagerUtil.getEntityManager();
	private Class<T> clazz;
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = entityManager.getTransaction();
		try {
		tx.begin();
		action.accept(entityManager);
		tx.commit();
		} catch (RuntimeException e) {
		tx.rollback();
		throw e;
		}
		}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Optional<T> get(long id) //si existe el objeto con id 10 en el registro de mi tabla me lo devuelve, pero si no existe con Optional no nos daría errores
	{
		//Por ej para la entidad pedido pondría en vez de class pedido y por ej id 10
		//el método 10 es de la interfaz entityManager y todo eso se lo pasamos al método ofNullable de la clase Optional
		//nos permite gestionar las excepciones de null pointer exception haciendo que no me devuelve una excepción, no devuelve null ni la excepción
		//si no encuentra el valor, osea es nulo, no hace nada
		return Optional.ofNullable(entityManager.find(clazz, id));
	}

	@Override
	public List<T> getAll() {
		//como estamos trabajando con hibernate en vez de SQL es HQL, en vez de select * from nosedonde lo ponemos como vemos
		String qlString = "FROM " + clazz.getName();
		Query query = entityManager.createQuery(qlString);
		return query.getResultList();
	}

	//
	@Override
	public void save(T t) {
		//Llamamos al método persist de entityManager y le pasamos como parámetro el objeto que queremos crear en la bd
		executeInsideTransaction(entityManager -> entityManager.persist(t));
	}

	@Override
	public void update(T t) {
		executeInsideTransaction(entityManager -> entityManager.merge(t));
	}

	@Override
	public void delete(T t) {
		executeInsideTransaction(entityManager -> entityManager.remove(t));
	}

}

