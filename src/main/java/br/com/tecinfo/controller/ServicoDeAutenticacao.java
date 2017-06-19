package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Funcionario;

public interface ServicoDeAutenticacao {

	public Funcionario autenticar(String usuario, String senha);

}
