package br.com.tecinfo.controller;

import br.com.tecinfo.controller.Authenticator;
import br.com.tecinfo.Deployments;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AuthenticatorTest { 

	@Deployment
	public static WebArchive createDeployment() {
		return Deployments.createInternalDeployment();
	}

	@Inject
	Authenticator authenticator;

	@Test
	public void autenticate_validUser_ok() {
		assertNotNull(authenticator.authenticate("demo","demo"));
	}
}
