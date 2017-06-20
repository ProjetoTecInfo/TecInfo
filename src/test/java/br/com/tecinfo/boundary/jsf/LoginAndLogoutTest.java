package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.junit.Assert.assertTrue;

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

    @FindBy(id = "formlogin:usuario:usuarioInput") // 1. injects an element by default
    // location strategy ("idOrName")
    private WebElement userName;

    @FindBy(id = "formlogin:senha:senhaInput")
    private WebElement password;

    @FindBy(id = "formlogin:buttonPanel:btnLogin")
    private WebElement loginButton;

    @FindBy(id = "formMenu:btnLogout")
    private WebElement loginLogout;

    @FindBy(id = "welcome")
    private WebElement welcome;

    @Test
    public void login_e_logout_usuarioValido() {
        browser.get(deploymentUrl.toExternalForm() + "login.jsf");
        assertTrue(browser.getTitle().contains("Login"));

        userName.sendKeys("admin");
        password.sendKeys("admin");

        guardHttp(loginButton).click();
        assertTrue(browser.getTitle().contains("Home"));
        assertTrue(String.format("deveria conter \"%s\", mas o valor encontrado foi :\"%s\"", "Administrador", welcome.getText()),
                welcome.getText().trim().contains("Administrador"));

        guardHttp(loginLogout).click();
        assertTrue(browser.getTitle().contains("Login"));
    }

}