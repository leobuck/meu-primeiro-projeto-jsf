package br.com.cursojsf.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.util.JPAUtil;

@Named
public class DAOGeneric<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private JPAUtil jpaUtil;
	
	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager.persist(entidade);
		
		transaction.commit();
	}
	
	public E merge(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		E entidadeRetorno = entityManager.merge(entidade);
		
		transaction.commit();
		
		return entidadeRetorno;
	}
	
	public void delete(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		entityManager.remove(entidade);
		
		transaction.commit();
	}
	
	public void deletePorId(E entidade) {		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		Object id = jpaUtil.getPrimaryKey(entidade);
		
		entityManager
			.createQuery("DELETE FROM " + entidade.getClass().getSimpleName() + " WHERE id = :id")
			.setParameter("id", id)
			.executeUpdate();
		
		transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> listar(Class<E> entidade) {		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		List<E> lista = entityManager
				.createQuery("FROM " + entidade.getName())
				.getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	public E consultar(Class<E> entidade, String codigo) {		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		E objeto = (E) entityManager.find(entidade, Long.parseLong(codigo));
		
		transaction.commit();
		
		return objeto;
	}
	
}
