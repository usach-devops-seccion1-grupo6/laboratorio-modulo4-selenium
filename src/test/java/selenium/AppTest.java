package selenium;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Dictionary;
import java.util.Hashtable;
/**
 * Unit test for simple App.
 */
public class AppTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");

        URL url;
        try {
                url = new URL("http://sfirefox:4444");
                driver = new RemoteWebDriver(url, options);
        } catch (MalformedURLException e) {
                e.printStackTrace();
        }

        //driver = new ChromeDriver(options);
        //driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
    }

    public Dictionary maqueta (int ahorro, int sueldo) {
        driver.get("http://localhost:9090/");

        driver.findElement(By.id("ahorro")).clear();
        driver.findElement(By.id("ahorro")).sendKeys(Integer.toString(ahorro));

        driver.findElement(By.id("sueldo")).clear();
        driver.findElement(By.id("sueldo")).sendKeys(Integer.toString(sueldo));

        driver.findElement(By.id("calcular")).click();

        WebDriverWait waitTemp = new WebDriverWait(driver, 30);
        waitTemp.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("resultado"))));

        Dictionary resultado = new Hashtable();

        resultado.put("diez", driver.findElement(By.id("diez")).getText());
        resultado.put("impuesto", driver.findElement(By.id("impuesto")).getText());
        resultado.put("saldo", driver.findElement(By.id("saldo")).getText());

        driver.close();
        return resultado;
    }


    @Test
    public void test1() {
        Dictionary resultado = maqueta(200000000, 4500000);
        System.out.println("diez: " + resultado.get("diez"));
        System.out.println("impuesto: " + resultado.get("impuesto"));
        System.out.println("saldo: " + resultado.get("saldo"));

        assertEquals(resultado.get("impuesto"), "Si");
    }

    @Test
    public void test2() {
        Dictionary resultado = maqueta(100000000, 1000000);
        System.out.println("diez: " + resultado.get("diez"));
        System.out.println("impuesto: " + resultado.get("impuesto"));
        System.out.println("saldo: " + resultado.get("saldo"));

        assertEquals(resultado.get("impuesto"), "No");
    }
}
