package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Cliente;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GerenciadorDeClientesEJB implements GerenciadorDeClientes {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    private Cliente clienteCorrente;

    @Override
    public List<Cliente> filtrarPor(FiltroCliente filtro) {
        return filtro.getTipoDeFiltro().filtrar(this, filtro);
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
    public List<Cliente> listarTodos() {
        TypedQuery<Cliente> query = em.createNamedQuery(Cliente.ListarTodos, Cliente.class);
        return query.getResultList();
    }

    @Override
    public Cliente getCliente(Long codigo) {
        this.clienteCorrente = this.em.find(Cliente.class, codigo);
        return getCorrente();
    }

    @Override
    public Cliente getCorrente() {
        return clienteCorrente;
    }

    @Override
    public void criar(Cliente cliente) {
        em.persist(cliente);
        this.clienteCorrente = cliente;
    }

    @Override
    public void remover(Long codigo) {
        Cliente ref = em.getReference(Cliente.class, codigo);
        em.clear();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void salvar() {
        //nada a fazer
    }

    @Override
    public void atualizar() {
        if (getCorrente() != null && getCorrente().getCodigo() != null) {
            this.em.refresh(getCorrente());
        }
    }

    @Override
    public void removerCorrente() {
        this.em.remove(getCorrente());
        this.clienteCorrente = null;
    }

    @Override
    @Remove
    public void fechar() {
        //nada a fazer
    }


}
