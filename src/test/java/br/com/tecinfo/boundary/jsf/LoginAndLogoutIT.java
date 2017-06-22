package br.com.tecinfo.boundary.jsf;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//import org.jboss.arquillian.drone.api.annotation.Drone;


public class LoginAndLogoutIT extends IntegrationBase {


    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    @RunAsClient
    public void testLoginAndLogout() throws Exception {
        browser().get(baseUrl.toExternalForm() + "login.jsf");
        browser().findElement(By.id("formlogin:usuario:usuarioInput")).clear();
        browser().findElement(By.id("formlogin:usuario:usuarioInput")).sendKeys("admin");
        browser().findElement(By.id("formlogin:senha:senhaInput")).clear();
        browser().findElement(By.id("formlogin:senha:senhaInput")).sendKeys("admin");
        browser().findElement(By.id("formlogin:buttonPanel:btnLogin")).click();
        assertEquals("Usuário: Administrador", browser().findElement(By.id("welcome")).getText());
        browser().findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Por favor, Identifique-se:", browser().findElement(By.xpath("//div[@id='top']/div/h3")).getText());
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
