package br.com.tecinfo.controller;

import java.util.List;

import br.com.tecinfo.entity.Cliente;

public interface GerenciadorDeClientes {

	List<Cliente> pesquisarPorCodigo(Long codigo);

	List<Cliente> pesquisarPorNome(String nome);

	List<Cliente> listarTodos();

	Cliente adicionar(Cliente cliente);

	Cliente pegarClientePorCodigo(Long codigo);

	Cliente alterar(Cliente cliente);

	void remover(Cliente cliente);

	Cliente adicionarEmailAoCliente(Cliente cliente, String email);

	Cliente removerEmailAoCliente(Cliente cliente, String email);

}
