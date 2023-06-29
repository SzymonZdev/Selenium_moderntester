package tests;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasicPages;

public class BasicsTests extends BaseTest {

    //                              <<<<<<Alerts>>>>>>
    // 1.1 - Simple Alert Pop up
    @Test
    public void simple_alert_test() {
        BasicPages basicPage = new BasicPages(browser);
        basicPage.go("alerts.php");
        basicPage.clickSimpleAlert();
        Alert alert = browser.driver.switchTo().alert();
        alert.accept();

        Assert.assertEquals(basicPage.getSimpleAlertLabelText(), "OK button pressed");
    }

    // 1.2 - Prompt Alert box
    @Test
    public void prompt_alert_box_test() {
        BasicPages basicPages = new BasicPages(browser);
        basicPages.go("alerts.php");
        basicPages.clickPromptAlert();
        Alert alert = browser.driver.switchTo().alert();
        alert.sendKeys("Simon");
        alert.accept();

        Assert.assertEquals(basicPages.getPromptAlertLabelText(), "Hello Simon! How are you today?");
    }

    // 1.3 - Confirm Alert Box
    @Test
    public void confirm_alert_box_test() {
        BasicPages basicPages = new BasicPages(browser);
        basicPages.go("alerts.php");
        basicPages.clickConfirmAlert();
        Alert alert = browser.driver.switchTo().alert();
        alert.accept();

        Assert.assertEquals(basicPages.getConfirmAlertLabelText(), "You pressed OK!");
        basicPages.clickConfirmAlert();
        alert = browser.driver.switchTo().alert();
        alert.dismiss();

        Assert.assertEquals(basicPages.getConfirmAlertLabelText(), "You pressed Cancel!");
    }

    // 1.4 - Delayed alert
    @Test
    public void delayed_alert_test() {
        BasicPages basicPages = new BasicPages(browser);
        basicPages.go("alerts.php");
        basicPages.clickDelayedAlert();
        browser.wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = browser.driver.switchTo().alert();
        alert.accept();

        Assert.assertEquals(basicPages.getDelayedAlertLabelText(), "OK button pressed");
    }

    //                              <<<<<<Form>>>>>>
    // 2 - Form test
    @Test
    public void form_fill_test() {
        BasicPages basicPages = new BasicPages(browser)
                .go("form.php")
                .fillFirstName("Szyms")
                .fillLastName("Surname")
                .fillEmail("email@email.com")
                .chooseRandomSex()
                .fillAge(32)
                .chooseRandomExperience()
                .chooseProfession("Automation Tester")
                .chooseRandomContinent()
                .chooseSeleniumCommands("Switch Commands", "Wait Commands")
                .uploadFile()
                .confirmForm();

        Assert.assertEquals(basicPages.getSubmitMessageText(), "Form send with success");
    }

    //                              <<<<<<Iframe>>>>>>
    // 3 - Iframe test (broken iframes, commenting out)
//    @Test
//    public void iFrame() {
//        browser.driver.switchTo().frame("iframe1");
//        browser.driver.findElement(By.id("inputFirstName3")).sendKeys("Szyms");
//        browser.driver.findElement(By.id("inputSurname3")).sendKeys("Surname");
//        browser.driver.switchTo().defaultContent();
//        browser.driver.switchTo().frame("iframe2");
//        browser.driver.findElement(By.id("inputLogin")).sendKeys("Szyms");
//        browser.driver.findElement(By.id("inputPassword")).sendKeys("Password123");
//        browser.driver.switchTo().defaultContent();
//    }
}
