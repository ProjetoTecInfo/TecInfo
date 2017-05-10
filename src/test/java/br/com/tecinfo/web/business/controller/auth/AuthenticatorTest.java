package br.com.tecinfo.web.business.controller.auth;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.tecinfo.web.Deployments;
import br.com.tecinfo.web.business.controller.auth.Authenticator;
import br.com.tecinfo.web.business.exception.BusinessException;
import br.com.tecinfo.web.business.exception.InfrastructureException;

@RunWith(Arquillian.class)
public class AuthenticatorTest { 

	@Deployment
	public static WebArchive createDeployment() {
		return Deployments.createInternalDeployment();
	}

	@Inject
	Authenticator authenticator;

	@Test
	public void autenticate_validUser_ok() throws BusinessException, InfrastructureException {
		assertNotNull(authenticator.authenticate("demo","demo"));
	}
}
