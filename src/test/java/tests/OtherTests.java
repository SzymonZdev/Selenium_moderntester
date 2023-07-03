package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasicPages;

import java.io.IOException;

public class OtherTests extends BaseTest{

    //                              <<<<<<Java Executor>>>>>>
    // 1 - "High Site" - js scroll test
    @Test
    public void js_scroll_test() {
        BasicPages basicPages = new BasicPages(browser);
        basicPages.go("high-site.php");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) browser.driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center', inline:'center'});", basicPages.getSubmitScrollButtonParagraph());
        Assert.assertTrue(basicPages.scrollSubmitButtonIsClickable());
        try {
            browser.takeScreenshotAndSave("js_scroll_test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
