package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class Browser {
    public final WebDriver driver;
    public final String baseURL;
    public final WebDriverWait wait;

    public Browser(WebDriver driver) {
        this.driver = driver;
        this.baseURL = "http://seleniumui.moderntester.pl/";
        this.wait = new WebDriverWait(driver, 5);
    }

    public WebElement getRandomWebElement(List<WebElement> webElements) {
        int random = new Random().nextInt(webElements.size());
        return webElements.get(random);
    }
}
