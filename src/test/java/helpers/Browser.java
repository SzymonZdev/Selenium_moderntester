package helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void switchToNewlyOpenedHandle(String oldHandle) {
        for (String windowHandle : driver.getWindowHandles()) {
            if(!oldHandle.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void closeNewHandleAndGoBackToOriginal(String mainHandle) {
        driver.close();
        driver.switchTo().window(mainHandle);
    }

    public void takeScreenshotAndSave(String testName) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./Screenshots/" + testName + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".png"));
    }
}
