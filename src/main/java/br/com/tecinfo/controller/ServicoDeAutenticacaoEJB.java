package br.com.tecinfo.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.tecinfo.entity.Funcionario;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ServicoDeAutenticacaoEJB implements ServicoDeAutenticacao {

	@PersistenceContext
	EntityManager em;

	@Override
	public Funcionario autenticar(String usuario, String senha) {

		TypedQuery<Funcionario> query = Funcionario.pesquisarPorUsuario(em, usuario);

		List<Funcionario> result = query.getResultList();

		if (result.isEmpty())
			return null;
		Funcionario func = result.iterator().next();
		if (java.util.Objects.equals(func.getSenha(), senha)) {
			return func;
		}
		return null;
	}

}
