package br.com.tecinfo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.tecinfo.entity.Cliente;

@Stateless
@Local(Clientes.class)
public class ClientesBean implements Clientes {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Cliente> procurar(FiltroCliente filtro) {
		if (filtro == null)
			return new ArrayList<>();
		if (filtro.getTipoDeFiltro() == null)
			return new ArrayList<>();
		switch (filtro.getTipoDeFiltro()) {
		case POR_CODIGO:
			return procurarPorCodigo(filtro.getCodigoRef());
		case POR_NOME:
			return procurarPorNome(filtro.getNomeRef());
		default:
			return new ArrayList<>();
		}
	}

	private List<Cliente> procurarPorNome(String nomeRef) {
		TypedQuery<Cliente> query = em
				.createNamedQuery(nomeRef == null ? Cliente.ListarTodos : Cliente.PesquisarPorNome, Cliente.class);
		if (nomeRef != null)
			query.setParameter(1, nomeRef.trim());
		return query.getResultList();
	}

	private List<Cliente> procurarPorCodigo(Long codigoRef) {
		TypedQuery<Cliente> query = em
				.createNamedQuery(codigoRef == null ? Cliente.ListarTodos : Cliente.PesquisarPorNome, Cliente.class);
		if (codigoRef != null)
			query.setParameter(1, codigoRef);
		return query.getResultList();
	}

}
