package br.com.cursojsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cursojsf.dao.DAOGeneric;
import br.com.cursojsf.entidades.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private DAOGeneric<Pessoa> daoPessoa = new DAOGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	public String salvar() {
		pessoa = daoPessoa.merge(pessoa);
		carregarPessoas();
		return "";
	}
	
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public String remover() {
		daoPessoa.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		return "";
	}
	
	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoPessoa.listar(Pessoa.class);
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
	
}
