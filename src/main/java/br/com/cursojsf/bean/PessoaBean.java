package br.com.cursojsf.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import br.com.cursojsf.dao.DAOGeneric;
import br.com.cursojsf.entidades.Cidades;
import br.com.cursojsf.entidades.Estados;
import br.com.cursojsf.entidades.Pessoa;
import br.com.cursojsf.repository.IPessoaDao;
import br.com.cursojsf.repository.PessoaDaoImpl;
import br.com.cursojsf.util.JPAUtil;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private DAOGeneric<Pessoa> daoPessoa = new DAOGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IPessoaDao pessoaDao = new PessoaDaoImpl();
	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	
	public String salvar() {
		pessoa = daoPessoa.merge(pessoa);
		carregarPessoas();
		mostrarMensagem("Cadastro com sucesso!");
		return "";
	}
	
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public String limpar() {
		pessoa = new Pessoa();
		return "";
	}
	
	public String remover() {
		daoPessoa.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMensagem("Removido com sucesso!");
		return "";
	}
	
	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoPessoa.listar(Pessoa.class);
	}
	
	public String logar() {
		Pessoa user = pessoaDao.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if (user != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", user);
			
			return "pessoa.jsf";
		}
		
		return "index.jsf";
	}
	
	public String sair() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");
		
		HttpServletRequest request = (HttpServletRequest) context.getCurrentInstance().getExternalContext().getRequest();
		
		request.getSession().invalidate();
		
		return "index.jsf";
	}
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa user = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return user.getPerfilUsuario().equals(acesso);
	}
	
	private void mostrarMensagem(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(mensagem);
		context.addMessage(null, message);
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		System.out.println("Método pesquisaCep iniciado");
		System.out.println("CEP: " + pessoa.getCep());
		
		try {
			
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			System.out.println(jsonCep);
			
			Pessoa pessoaGson = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setLogradouro(pessoaGson.getLogradouro());
			pessoa.setComplemento(pessoaGson.getComplemento());
			pessoa.setBairro(pessoaGson.getBairro());
			pessoa.setLocalidade(pessoaGson.getLocalidade());
			pessoa.setUf(pessoaGson.getUf());
			
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensagem("Erro ao consultar CEP");
		}
	}
	
	public void carregaCidades(AjaxBehaviorEvent event) {
		Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();
					
		if (estado != null) {
			pessoa.setEstados(estado);
			
			List<Cidades> cidades = JPAUtil.getEntityManager()
					.createQuery("FROM Cidades WHERE estados.id = :codEstado")
					.setParameter("codEstado", estado.getId())
					.getResultList();
			
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
			}
			
			setCidades(selectItemsCidade);
		}
	}
	
	public void editar() {
		if (pessoa.getCidades() != null) {
			Estados estado = pessoa.getCidades().getEstados();
			pessoa.setEstados(estado);
			
			List<Cidades> cidades = JPAUtil.getEntityManager()
					.createQuery("FROM Cidades WHERE estados.id = :codEstado")
					.setParameter("codEstado", estado.getId())
					.getResultList();
			
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
			}
			
			setCidades(selectItemsCidade);
		}
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DAOGeneric<Pessoa> getDaoPessoa() {
		return daoPessoa;
	}

	public void setDaoPessoa(DAOGeneric<Pessoa> daoPessoa) {
		this.daoPessoa = daoPessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<SelectItem> getEstados() {
		estados = pessoaDao.listaEstados();
		return estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}
	
}
