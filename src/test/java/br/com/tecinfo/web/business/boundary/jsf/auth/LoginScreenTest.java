package br.com.tecinfo.web.business.boundary.jsf.auth;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.tecinfo.web.Deployments;

@RunWith(Arquillian.class)
public class LoginScreenTest {

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return Deployments.createLoginScreenDeployment();
	}

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@FindBy(id = "loginForm:userName") // 1. injects an element by default
										// location strategy ("idOrName")
	private WebElement userName;

	@FindBy(id = "loginForm:password")
	private WebElement password;

	@FindBy(id = "loginForm:login")
	private WebElement loginButton;

	@FindBy(tagName = "li") // 2. injects a first element with given tag name
	private WebElement facesMessage;

	@FindByJQuery("p:visible") // 3. injects an element using jQuery selector
	private WebElement signedAs;

	@FindBy(css = "input[type=submit]")
	private WebElement whoAmI;

	@Test
	public void should_login_successfully() {
		browser.get(deploymentUrl.toExternalForm() + "login.jsf"); // first page
																	// load
																	// doesn't
																	// have to
																	// be
																	// synchronized

		userName.sendKeys("demo");
		password.sendKeys("demo");

		guardHttp(loginButton).click(); // 1. synchronize full-page request
		assertEquals("Welcome", facesMessage.getText().trim());

		guardAjax(whoAmI).click(); // 2. synchronize AJAX request
		assertTrue(signedAs.getText().contains("demo"));
	}

}