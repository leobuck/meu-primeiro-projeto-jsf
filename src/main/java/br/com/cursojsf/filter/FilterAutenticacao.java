package br.com.cursojsf.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.entidades.Pessoa;
import br.com.cursojsf.util.JPAUtil;

@WebFilter(urlPatterns = {"/*"})
public class FilterAutenticacao implements Filter {

	@Inject
	private JPAUtil jpaUtil;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("Invocando filter");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
		
		String url = req.getServletPath();
		
		if (!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return;
		} else {
			chain.doFilter(request, response);
		}
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		EntityManager entityManager = null;
		try {
			entityManager = jpaUtil.getEntityManager();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

}
