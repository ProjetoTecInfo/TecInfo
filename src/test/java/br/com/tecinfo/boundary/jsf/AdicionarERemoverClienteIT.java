package br.com.tecinfo.boundary.jsf;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import static org.junit.Assert.*;

//import org.jboss.arquillian.drone.api.annotation.Drone;

public class AdicionarERemoverClienteIT extends IntegrationBase {

    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    @RunAsClient
    public void testAdicionarERemoverCliente() throws Exception {
        browser().get(baseUrl.toExternalForm() + "login.jsf");
        browser().findElement(By.id("formlogin:usuario:usuarioInput")).clear();
        browser().findElement(By.id("formlogin:usuario:usuarioInput")).sendKeys("admin");
        browser().findElement(By.id("formlogin:senha:senhaInput")).clear();
        browser().findElement(By.id("formlogin:senha:senhaInput")).sendKeys("admin");
        browser().findElement(By.id("formlogin:buttonPanel:btnLogin")).click();
        browser().findElement(By.linkText("Clientes")).click();
        browser().findElement(By.id("formClientes:btnAdicionarCliente")).click();
        browser().findElement(By.id("formProtegido:soAtendentes:formClientes:nome:nomeInput")).clear();
        browser().findElement(By.id("formProtegido:soAtendentes:formClientes:nome:nomeInput")).sendKeys("Maximillian");
        browser().findElement(By.cssSelector("i.glyphicon.glyphicon-check")).click();
        browser().findElement(By.id("formProtegido:soAtendentes:formClientes:buttonPanel:btnAdicionarCliente")).click();
        browser().findElement(By.id("formProtegido:soAtendentes:formClientes:buttonPanel:btnPesquisarClientes")).click();
        browser().findElement(By.id("formClientes:nomeRef")).clear();
        browser().findElement(By.id("formClientes:nomeRef")).sendKeys("Max");
        browser().findElement(By.id("formClientes:btnClientePesquisa")).click();
        assertEquals("Clientes encontrados", browser().findElement(By.xpath("//form[@id='formClientes']/div[4]/div/h4")).getText());
        browser().findElement(By.cssSelector("i.glyphicon.glyphicon-zoom-in")).click();
        browser().findElement(By.id("formProtegido:soAtendentes:formClientes:buttonPanel:btnCancelar")).click();
//    assertTrue(closeAlertAndGetItsText().matches("^Quer [\\s\\S]*$"));
        confirmDialog();

        assertTrue(isElementPresent(By.xpath("//div[@id='content']/ul/li")));
        assertEquals("Nenhum cliente encontrado", browser().findElement(By.xpath("//form[@id='formClientes']/div[4]/h4")).getText());
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

    private void confirmDialog() {
        try {
            if (browser() instanceof PhantomJSDriver) {
                PhantomJSDriver phantom = (PhantomJSDriver) browser();
                phantom.executeScript("window.alert = function(){}");
                phantom.executeScript("window.confirm = function(){return true;}");
            } else {
                Alert alert = browser().switchTo().alert();
                String alertText = alert.getText();
                if (acceptNextAlert) {
                    alert.accept();
                } else {
                    alert.dismiss();
                }
            }
        } finally {
            acceptNextAlert = true;
        }
    }
}
