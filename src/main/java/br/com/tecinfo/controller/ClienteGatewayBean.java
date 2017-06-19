package br.com.tecinfo.controller;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.tecinfo.entity.Cliente;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Local(ClienteGateway.class)
public class ClienteGatewayBean implements ClienteGateway {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	private Cliente clienteCorrente;

	@Override
	public Cliente procurar(Long codigo) {
		this.clienteCorrente = em.find(Cliente.class, codigo);
		return this.clienteCorrente;
	}

	@Override
	public Cliente getCorrente() {
		return this.clienteCorrente;
	}

	@Override
	public void criar(Cliente cliente) {
		this.em.persist(cliente);
		this.clienteCorrente = cliente;
	}

	@Override
	public void remover(Long codigo) {
		Cliente ref = this.em.getReference(Cliente.class, codigo);
		this.em.remove(ref);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void salvar() {
		// data a fazer
	}

	@Override
	public void atualizar() {
		this.em.refresh(this.clienteCorrente);
	}

	@Override
	public void removerCorrente() {
		this.em.remove(this.clienteCorrente);
		this.clienteCorrente = null;
	}

	@Remove
	@Override
	public void fecharGateway() {
		// TODO Auto-generated method stub

	}

}
