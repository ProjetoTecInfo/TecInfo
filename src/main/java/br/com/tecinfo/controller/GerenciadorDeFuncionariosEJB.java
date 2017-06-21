package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Funcionario;

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
public class GerenciadorDeFuncionariosEJB implements GerenciadorDeFuncionarios {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    private Funcionario entidadeCorrente;

    @Override
    public List<Funcionario> listarTodos() {
        TypedQuery<Funcionario> query = em.createNamedQuery(Funcionario.ListarTodos, Funcionario.class);
        return query.getResultList();
    }

    @Override
    public Funcionario getFuncionario(Long codigo) {
        this.entidadeCorrente = this.em.find(Funcionario.class, codigo);
        return getCorrente();
    }

    @Override
    public Funcionario getCorrente() {
        return entidadeCorrente;
    }

    @Override
    public void criar(Funcionario cliente) {
        em.persist(cliente);
        this.entidadeCorrente = cliente;
    }

    @Override
    public void remover(Long codigo) {
        Funcionario ref = em.getReference(Funcionario.class, codigo);
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
        this.entidadeCorrente = null;
    }

    @Override
    @Remove
    public void fechar() {
        //nada a fazer
    }


}
