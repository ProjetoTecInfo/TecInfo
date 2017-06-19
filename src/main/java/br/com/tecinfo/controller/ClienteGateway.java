package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Cliente;

public interface ClienteGateway {

	public Cliente procurar(Long codigo);

	public Cliente getCorrente();

	public void criar(Cliente cliente);

	public void remover(Long codigo);

	public void salvar();

	public void atualizar();

	public void removerCorrente();

	public void fecharGateway();

}
