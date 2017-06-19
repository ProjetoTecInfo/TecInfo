package br.com.tecinfo.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.tecinfo.entity.Cliente;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GerenciadorDeClientesEJB implements GerenciadorDeClientes {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Cliente> listarTodos() {
		TypedQuery<Cliente> query = em.createNamedQuery(Cliente.ListarTodos, Cliente.class);
		return query.getResultList();
	}

	@Override
	public List<Cliente> pesquisarPorCodigo(Long codigo) {
		TypedQuery<Cliente> query = em.createNamedQuery(Cliente.PesquisarPorCodigo, Cliente.class);
		query.setParameter(1, codigo);
		return query.getResultList();
	}

	@Override
	public List<Cliente> pesquisarPorNome(String nome) {
		TypedQuery<Cliente> query = em.createNamedQuery(Cliente.PesquisarPorNome, Cliente.class);
		query.setParameter(1, nome);
		return query.getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente adicionar(Cliente cliente) {
		em.persist(cliente);
		return cliente;
	}

	@Override
	public Cliente pegarClientePorCodigo(Long codigo) {
		em.clear();
		return em.find(Cliente.class, codigo);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente alterar(Cliente cliente) {
		em.clear();
		em.merge(cliente);
		return cliente;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Cliente cliente) {
		Cliente client = pegarClientePorCodigo(cliente.getCodigo());
		client.removeTodosEmails();
		em.remove(client);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente adicionarEmailAoCliente(Cliente cliente, String email) {
		Cliente c = pegarClientePorCodigo(cliente.getCodigo());
		c.adicionarEmail(email);
		return c;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente removerEmailAoCliente(Cliente cliente, String email) {
		Cliente c = pegarClientePorCodigo(cliente.getCodigo());
		c.removeEmail(email);
		return c;
	}

}
