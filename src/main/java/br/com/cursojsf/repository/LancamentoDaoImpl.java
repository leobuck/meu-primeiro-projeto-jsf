package br.com.cursojsf.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.entidades.Lancamento;
import br.com.cursojsf.util.JPAUtil;

public class LancamentoDaoImpl implements ILancamentoDao{

	@Override
	public List<Lancamento> consultar(Long idUsuario) {
		List<Lancamento> lista = new ArrayList<Lancamento>();
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery("FROM Lancamento WHERE usuario.id = :idUsuario")
				.setParameter("idUsuario", idUsuario)
				.getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return lista;
	}

}
