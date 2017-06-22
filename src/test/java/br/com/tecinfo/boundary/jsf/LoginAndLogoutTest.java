package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

import static org.junit.Assert.assertTrue;

//import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@RunWith(Arquillian.class)
public class LoginAndLogoutTest extends BaseIT {

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.createLoginScreenDeployment();
    }

    @ArquillianResource
    private URL deploymentUrl;

    @FindBy(id = "formlogin:usuario:usuarioInput") // 1. injects an element by default
    // location strategy ("idOrName")
    private WebElement userName;

    @FindBy(id = "formlogin:senha:senhaInput")
    private WebElement password;

    @FindBy(id = "formlogin:buttonPanel:btnLogin")
    private WebElement loginButton;

    @Test
    @RunAsClient
    public void login_e_logout_usuarioValido() {
        
        browser().manage().window().maximize();//.setSize(new Dimension(1920,1080));
        browser().get(deploymentUrl.toExternalForm() + "login.jsf");
        assertTrue(browser().getTitle().contains("Login"));

        userName.sendKeys("admin");
        password.sendKeys("admin");

//        guardHttp(loginButton).click();
        loginButton.click();
        assertTrue(browser().getTitle().contains("Home"));

        WebElement welcome = browser().findElement(By.id("welcome"));
        assertTrue(String.format("deveria conter \"%s\", mas o valor encontrado foi :\"%s\"", "Administrador", welcome.getAttribute("innerHTML").trim()),
                welcome.getAttribute("innerHTML").trim().contains("Administrador"));

        
//        guardHttp(browser().findElement(By.id("formMenu:btnLogout"))).click();
        browser().findElement(By.id("formMenu:btnLogout")).click();
        assertTrue(browser().getTitle().contains("Login"));
    }

}
