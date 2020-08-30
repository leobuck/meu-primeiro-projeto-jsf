package br.com.cursojsf.dao;

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
}
