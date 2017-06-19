package br.com.tecinfo.boundary.jsf;

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

import br.com.tecinfo.Deployments;

@RunWith(Arquillian.class)
public class LoginAndLogoutTest {

	@Deployment
	public static WebArchive createDeployment() {
		return Deployments.createLoginScreenDeployment();
	}

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@FindBy(id = "form:usuario") // 1. injects an element by default
									// location strategy ("idOrName")
	private WebElement userName;

	@FindBy(id = "form:senha")
	private WebElement password;

	@FindBy(id = "form:btnLogin")
	private WebElement loginButton;

	@FindBy(id = "form:btnLogout")
	private WebElement loginLogout;

	@FindByJQuery("span.ui-menuitem-text:first")
	private WebElement fistItemMenu;

	@Test
	public void login_e_logout_usuarioValido() {
		browser.get(deploymentUrl.toExternalForm() + "login.jsf"); 
		assertTrue(browser.getTitle().contains("Login"));

		userName.sendKeys("admin");
		password.sendKeys("admin");

		guardHttp(loginButton).click(); 
		assertTrue(browser.getTitle().contains("Home"));
		assertEquals("File", fistItemMenu.getText().trim());

		guardHttp(loginLogout).click(); 
		assertTrue(browser.getTitle().contains("Login"));
	}

}