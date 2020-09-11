package br.com.cursojsf.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.entidades.Pessoa;
import br.com.cursojsf.util.JPAUtil;

public class PessoaDaoImpl implements IPessoaDao {

	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		Pessoa pessoa = null;
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		pessoa = (Pessoa) entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.login = :login AND p.senha = :senha")
				.setParameter("login", login)
				.setParameter("senha", senha)
				.getSingleResult();
		
		transaction.commit();
		
		entityManager.close();
		
		return pessoa;
	}

}
