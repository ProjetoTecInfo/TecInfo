package br.com.tecinfo.boundary.jsf;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by marruda on 22/06/2017.
 */
public abstract class BaseIT {

    private WebDriver webDriver;

    @ArquillianResource
    protected URL baseUrl;


    private WebDriver getHtmlUnitDriver() {
        return new HtmlUnitDriver(true);
    }

    private WebDriver getPhantomJSDriver() {
        PhantomJsDriverManager.getInstance().version(System.getProperty("phantomjs.version", "2.1.1")).arch32().setup();
        return new PhantomJSDriver();
    }

    private WebDriver getChromeDriver() {
        ChromeDriverManager.getInstance().arch32().setup();
        return new ChromeDriver();
    }

    private WebDriver getFirefoxDriver() {
        FirefoxDriverManager.getInstance().arch32().setup();
        return new FirefoxDriver();
    }

    protected <T> T initElements(T object) {
        PageFactory.initElements(webDriver, object);
        return object;
    }

    public WebDriver browser() {
        return webDriver;
    }

    @Before
    public void setUp() throws Exception {
        if (webDriver == null) {
            String webDriverType = System.getProperty("browser", "chrome");
            if (webDriverType.equals("htmlunit")) {
                webDriver = getHtmlUnitDriver();
            } else if (webDriverType.equals("firefox")) {
                webDriver = getFirefoxDriver();
            } else if (webDriverType.equals("chrome")) {
                webDriver = getChromeDriver();
            } else if (webDriverType.equals("phantomjs")) {
                webDriver = getPhantomJSDriver();
            } else {
                webDriver = getChromeDriver();
            }
        }
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        initElements(this);
    }

    @After
    public void tearDown() throws Exception {
        webDriver.close();
        webDriver = null;
    }
}
