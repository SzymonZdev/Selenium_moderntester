package tests;

import helpers.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    Browser browser;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions().addArguments("--remote-allow-origins=*");
        browser = new Browser(new ChromeDriver(options));
    }

    @AfterMethod
    public void tearDown() {
        browser.driver.quit();
    }
}
