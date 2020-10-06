package br.com.cursojsf.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.entidades.Lancamento;

@Named
public class LancamentoDaoImpl implements ILancamentoDao, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultar(Long idUsuario) {
		List<Lancamento> lista = new ArrayList<Lancamento>();
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery("FROM Lancamento WHERE usuario.id = :idUsuario")
				.setParameter("idUsuario", idUsuario)
				.getResultList();
		
		transaction.commit();
		
		return lista;
	}

}
