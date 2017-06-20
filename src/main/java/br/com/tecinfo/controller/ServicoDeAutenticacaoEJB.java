package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Funcionario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ServicoDeAutenticacaoEJB implements ServicoDeAutenticacao {

    private static Logger logger = LoggerFactory.getLogger(ServicoDeAutenticacao.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public Funcionario autenticar(String usuario, String senha) {
        logger.info("inicializando a autenticação do usuário : {}", usuario);
        TypedQuery<Funcionario> query = Funcionario.pesquisarPorUsuario(em, usuario);

        List<Funcionario> result = query.getResultList();

        if (result.isEmpty()) {
            logger.info("usuário {} não encontrado", usuario);
            return null;
        }
        Funcionario func = result.iterator().next();
        if (java.util.Objects.equals(func.getSenha(), senha)) {
            return func;
        }
        logger.info("não pode autenticar o usuário {}, senha informada está inválida", usuario);
        return null;
    }

}
