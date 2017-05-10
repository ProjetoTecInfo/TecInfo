package br.com.tecinfo.web.business.controller.auth;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.tecinfo.web.business.entities.auth.User;
import br.com.tecinfo.web.business.exception.BusinessException;
import br.com.tecinfo.web.business.exception.InfrastructureException;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Authenticator {

	@PersistenceContext
	EntityManager em;

	public User authenticate(String username,String password) throws BusinessException, InfrastructureException {
		try {

			return em.createQuery("select distinct u from User u where u.username=:username and u.password=:password",
							User.class)
					.setParameter("username", username)
					.setParameter("password", password)
					.getSingleResult();

		} catch (NoResultException e) {
			throw new BusinessException("authentication failure");
		} catch (Exception e) {
			throw new InfrastructureException(e);
		}

	}

}
