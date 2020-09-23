package br.com.cursojsf.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.util.JPAUtil;

public class DAOGeneric<E> {

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager.persist(entidade);
		
		transaction.commit();
		
		entityManager.close();
	}
	
	public E merge(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		E entidadeRetorno = entityManager.merge(entidade);
		
		transaction.commit();
		
		entityManager.close();
		
		return entidadeRetorno;
	}
	
	public void delete(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager.remove(entidade);
		
		transaction.commit();
		
		entityManager.close();
	}
	
	public void deletePorId(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		Object id = JPAUtil.getPrimaryKey(entidade);
		
		entityManager
			.createQuery("DELETE FROM " + entidade.getClass().getSimpleName() + " WHERE id = :id")
			.setParameter("id", id)
			.executeUpdate();
		
		transaction.commit();
		
		entityManager.close();
	}
	
	public List<E> listar(Class<E> entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		List<E> lista = entityManager
				.createQuery("FROM " + entidade.getName())
				.getResultList();
		
		transaction.commit();
		
		entityManager.close();
		
		return lista;
	}
	
	public E consultar(Class<E> entidade, String codigo) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		E objeto = (E) entityManager.find(entidade, Long.parseLong(codigo));
		
		transaction.commit();
		
		entityManager.close();
		
		return objeto;
	}
	
}
