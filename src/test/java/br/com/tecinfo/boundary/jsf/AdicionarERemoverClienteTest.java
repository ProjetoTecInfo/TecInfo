package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AdicionarERemoverClienteTest {

  @Deployment
  public static WebArchive createDeployment() {
    return Deployments.createScreenDeployment("add-del-cliente");
  }

  @Drone
  private WebDriver driver;

  @ArquillianResource
  private URL baseUrl;

  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  @RunAsClient
  public void testAdicionarERemoverCliente() throws Exception {
    driver.get(baseUrl.toExternalForm() + "login.jsf");
    driver.findElement(By.id("formlogin:usuario:usuarioInput")).clear();
    driver.findElement(By.id("formlogin:usuario:usuarioInput")).sendKeys("admin");
    driver.findElement(By.id("formlogin:senha:senhaInput")).clear();
    driver.findElement(By.id("formlogin:senha:senhaInput")).sendKeys("admin");
    driver.findElement(By.id("formlogin:buttonPanel:btnLogin")).click();
    driver.findElement(By.linkText("Clientes")).click();
    driver.findElement(By.id("formClientes:btnAdicionarCliente")).click();
    driver.findElement(By.id("formProtegido:soAtendentes:formClientes:nome:nomeInput")).clear();
    driver.findElement(By.id("formProtegido:soAtendentes:formClientes:nome:nomeInput")).sendKeys("Maximillian");
    driver.findElement(By.cssSelector("i.glyphicon.glyphicon-check")).click();
    driver.findElement(By.id("formProtegido:soAtendentes:formClientes:buttonPanel:btnAdicionarCliente")).click();
    driver.findElement(By.id("formProtegido:soAtendentes:formClientes:buttonPanel:btnPesquisarClientes")).click();
    driver.findElement(By.id("formClientes:nomeRef")).clear();
    driver.findElement(By.id("formClientes:nomeRef")).sendKeys("Max");
    driver.findElement(By.id("formClientes:btnClientePesquisa")).click();
    assertEquals("Clientes encontrados", driver.findElement(By.xpath("//form[@id='formClientes']/div[4]/div/h4")).getText());
    driver.findElement(By.cssSelector("i.glyphicon.glyphicon-zoom-in")).click();
    driver.findElement(By.id("formProtegido:soAtendentes:formClientes:buttonPanel:btnCancelar")).click();
    assertTrue(closeAlertAndGetItsText().matches("^Quer [\\s\\S]*$"));
    assertTrue(isElementPresent(By.xpath("//div[@id='content']/ul/li")));
    assertEquals("Nenhum cliente encontrado", driver.findElement(By.xpath("//form[@id='formClientes']/div[4]/h4")).getText());
    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @After
  public void tearDown() throws Exception {
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
