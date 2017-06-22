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

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
public class LoginAndLogoutIT {

  @Deployment
  public static WebArchive createDeployment() {
    return Deployments.createLoginScreenDeployment();
  }

  @Drone
  private WebDriver driver;

  @ArquillianResource
  private String baseUrl;

  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  @RunAsClient
  public void testLoginAndLogout() throws Exception {
    driver.get(baseUrl + "/tecinfo-web/login.jsf");
    driver.findElement(By.id("formlogin:usuario:usuarioInput")).clear();
    driver.findElement(By.id("formlogin:usuario:usuarioInput")).sendKeys("admin");
    driver.findElement(By.id("formlogin:senha:senhaInput")).clear();
    driver.findElement(By.id("formlogin:senha:senhaInput")).sendKeys("admin");
    driver.findElement(By.id("formlogin:buttonPanel:btnLogin")).click();
    assertEquals("Usuário: Administrador", driver.findElement(By.id("welcome")).getText());
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Por favor, Identifique-se:", driver.findElement(By.xpath("//div[@id='top']/div/h3")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
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

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
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
