package br.com.cursojsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.cursojsf.dao.DAOGeneric;
import br.com.cursojsf.entidades.Lancamento;
import br.com.cursojsf.entidades.Pessoa;
import br.com.cursojsf.repository.ILancamentoDao;
import br.com.cursojsf.repository.LancamentoDaoImpl;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {

	private Lancamento lancamento = new Lancamento();
	private DAOGeneric<Lancamento> daoLancamento = new DAOGeneric<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<>();
	private ILancamentoDao lancamentoDao = new LancamentoDaoImpl();
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa user = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		lancamento.setUsuario(user);
		daoLancamento.merge(lancamento);
		
		carregarLancamentos();
		
		return "";
	}
	
	@PostConstruct
	private void carregarLancamentos() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa user = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		lancamentos = lancamentoDao.consultar(user.getId());
	}

	public String novo() {
		lancamento = new Lancamento();
		return "";
	}
	
	public String remover() {
		daoLancamento.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentos();
		return "";
	}
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa user = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return user.getPerfilUsuario().equals(acesso);
	}
	
	public Lancamento getLancamento() {
		return lancamento;
	}
	
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
	public DAOGeneric<Lancamento> getDaoLancamento() {
		return daoLancamento;
	}
	
	public void setDaoLancamento(DAOGeneric<Lancamento> daoLancamento) {
		this.daoLancamento = daoLancamento;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
}
