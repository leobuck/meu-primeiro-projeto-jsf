package br.com.cursojsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.cursojsf.entidades.Estados;
import br.com.cursojsf.util.JPAUtil;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
		
		transaction.commit();
		
		entityManager.close();
		
		return estados;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		if (estado == null) {
			return null;
		}
		
		if (estado instanceof Estados) {
			return ((Estados) estado).getId().toString();
		} else {
			return estado.toString();
		}
	}

}
