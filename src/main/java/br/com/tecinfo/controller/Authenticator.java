package br.com.tecinfo.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.tecinfo.entities.User;
import javax.persistence.PersistenceException;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Authenticator {

    @PersistenceContext
    EntityManager em;

    public User authenticate(String username, String password) throws NoResultException, PersistenceException {

        return em.createQuery("select distinct u from User u where u.username=:username and u.password=:password",
                User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();

    }

}
