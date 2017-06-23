package br.com.tecinfo.boundary.jsf;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.*;


public class AdicionarERemoverFuncionarioIT extends IntegrationBase {

    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    @RunAsClient
    public void test() throws Exception {
      // open | /tecinfo-web/login.jsf |
    browser().get(baseUrl.toExternalForm() + "login.jsf");
    // type | id=formlogin:usuario:usuarioInput | admin
    browser().findElement(By.id("formlogin:usuario:usuarioInput")).clear();
    browser().findElement(By.id("formlogin:usuario:usuarioInput")).sendKeys("admin");
    // type | id=formlogin:senha:senhaInput | admin
    browser().findElement(By.id("formlogin:senha:senhaInput")).clear();
    browser().findElement(By.id("formlogin:senha:senhaInput")).sendKeys("admin");
    // click | id=formlogin:buttonPanel:btnLogin | 
    browser().findElement(By.id("formlogin:buttonPanel:btnLogin")).click();
    // click | link=Funcionários | 
    browser().findElement(By.linkText("Funcionários")).click();
    // click | id=formProtegido:soAdministradores:formFuncionários:btnAdicionarFuncionario | 
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionários:btnAdicionarFuncionario")).click();
    // type | id=formProtegido:soAdministradores:formFuncionarios:nome:nomeInput | João Luis da Silva
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:nome:nomeInput")).clear();
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:nome:nomeInput")).sendKeys("João Luis da Silva");
    // click | id=formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnAdicionarFuncionario | 
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnAdicionarFuncionario")).click();
    // click | id=formProtegido:soAdministradores:formFuncionarios:papeisPanel:j_idt33:1:btnAdicionarPapel | 
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:papeisPanel:j_idt33:1:btnAdicionarPapel")).click();
    // click | id=formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnAdicionarFuncionario | 
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnAdicionarFuncionario")).click();
    // click | id=formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnPesquisarClientes | 
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnPesquisarClientes")).click();
    // assertText | //form[@id='formProtegido:soAdministradores:formFuncionários']/div[2]/div[2]/table/tbody[2]/tr[3]/td[4] | João Luis da Silva
    assertEquals("João Luis da Silva", browser().findElement(By.xpath("//form[@id='formProtegido:soAdministradores:formFuncionários']/div[2]/div[2]/table/tbody[2]/tr[3]/td[4]")).getText());
    // click | //form[@id='formProtegido:soAdministradores:formFuncionários']/div[2]/div[2]/table/tbody[2]/tr[3]/td/a/i | 
    browser().findElement(By.xpath("//form[@id='formProtegido:soAdministradores:formFuncionários']/div[2]/div[2]/table/tbody[2]/tr[3]/td/a/i")).click();
    // click | id=formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnCancelar | 
    browser().findElement(By.id("formProtegido:soAdministradores:formFuncionarios:buttonPanel:btnCancelar")).click();
    // assertConfirmation | Quer realmente remover o funcionário João Luis da Silva<jldsilva>? | 
    assertTrue(closeAlertAndGetItsText().matches("^Quer realmente remover o funcionário João Luis da Silva<jldsilva>[\\s\\S]$"));
    // assertBodyText | *Cliente João Luis da Silva<jldsilva> removido com sucesso!* | 
    assertTrue(browser().findElement(By.tagName("BODY")).getText().matches("^[\\s\\S]*Cliente João Luis da Silva<jldsilva> removido com sucesso![\\s\\S]*$"));
    // click | //button[@type='submit'] | 
    browser().findElement(By.xpath("//button[@type='submit']")).click();
  }

  @After
  public void tearDown() throws Exception {
    super.tearDown();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      browser().findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      browser().switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = browser().switchTo().alert();
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
