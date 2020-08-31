package br.com.cursojsf.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cursojsf.dao.DAOGeneric;
import br.com.cursojsf.entidades.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DAOGeneric<Pessoa> daoPessoa = new DAOGeneric<Pessoa>();
	
	public String salvar() {
		pessoa = daoPessoa.merge(pessoa);
		return "";
	}
	
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public String remover() {
		daoPessoa.deletePorId(pessoa);
		pessoa = new Pessoa();
		return "";
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
	
}
