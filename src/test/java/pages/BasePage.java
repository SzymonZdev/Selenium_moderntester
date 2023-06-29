package pages;

import helpers.Browser;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final Browser browser;
    protected final String baseURL;
    protected BasePage(Browser browser) {
        this.browser = browser;
        this.driver = browser.driver;
        baseURL = browser.baseURL;
    }
}
