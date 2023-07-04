package pages;

import helpers.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final Browser browser;
    protected final String baseURL;
    protected WebDriverWait wait;
    protected BasePage(Browser browser) {
        this.browser = browser;
        this.driver = browser.driver;
        baseURL = browser.baseURL;
        wait = new WebDriverWait(driver, 5);
    }
}
