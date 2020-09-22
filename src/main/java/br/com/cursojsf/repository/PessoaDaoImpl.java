package br.com.cursojsf.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.entidades.Estados;
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

	@Override
	public List<SelectItem> listaEstados() {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		List<Estados> estados = entityManager.createQuery("FROM Estados").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		
		transaction.commit();
		
		entityManager.close();
		
		return selectItems;
	}

}
