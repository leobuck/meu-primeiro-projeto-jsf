package br.com.cursojsf.repository;

import br.com.cursojsf.entidades.Pessoa;

public interface IPessoaDao {

	Pessoa consultarUsuario(String login, String senha);
	
}
