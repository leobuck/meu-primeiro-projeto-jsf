package br.com.cursojsf.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.entidades.Estados;
import br.com.cursojsf.entidades.Pessoa;

@Named
public class PessoaDaoImpl implements IPessoaDao, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		Pessoa pessoa = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		pessoa = (Pessoa) entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.login = :login AND p.senha = :senha")
				.setParameter("login", login)
				.setParameter("senha", senha)
				.getSingleResult();
		
		transaction.commit();
		
		return pessoa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectItem> listaEstados() {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		List<Estados> estados = entityManager.createQuery("FROM Estados").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		
		transaction.commit();
		
		return selectItems;
	}

}
