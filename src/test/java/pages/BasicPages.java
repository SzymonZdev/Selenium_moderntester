package pages;

import helpers.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class BasicPages extends BasePage {
    @FindBy(css = "#simple-alert")
    WebElement simpleAlert;
    @FindBy(css = "#simple-alert-label")
    WebElement simpleAlertLabel;
    @FindBy(css = "#prompt-alert")
    WebElement promptAlert;
    @FindBy(css = "#prompt-label")
    WebElement promptAlertLabel;
    @FindBy(css = "button[onclick='confirmPopUp()']")
    WebElement confirmAlert;
    @FindBy(css = "#confirm-label")
    WebElement confirmAlertLabel;
    @FindBy(id = "delayed-alert")
    WebElement delayedAlert;
    @FindBy(id = "delayed-alert-label")
    WebElement delayedAlertLabel;
    @FindBy(css = "#inputFirstName3")
    WebElement firstNameField;
    @FindBy(css = "#inputLastName3")
    WebElement lastNameField;
    @FindBy(css = "#inputEmail3")
    WebElement emailField;
    @FindAll(@FindBy(css = "input[name='gridRadiosSex']"))
    List<WebElement> sexRadioButtons;
    @FindBy(css = "#inputAge3")
    WebElement ageField;
    @FindAll(@FindBy(css = "input[name='gridRadiosExperience']"))
    List<WebElement> experienceRadioButtons;
    @FindAll(@FindBy(css = "input[name='gridCheckboxProfession']"))
    List<WebElement> professionCheckboxButtons;
    @FindBy(id = "selectContinents")
    WebElement selectContinentsField;
    @FindBy(id = "selectSeleniumCommands")
    WebElement selectSeleniumCommandsField;
    @FindBy(id = "chooseFile")
    WebElement uploadFileInput;
    @FindBy(css = "button.btn-primary[type = 'submit']")
    WebElement submitButton;
    @FindBy(css = "#validator-message")
    WebElement submitMessageField;


    public BasicPages(Browser browser) {
        super(browser);
        PageFactory.initElements(browser.driver, this);
    }

    public BasicPages go(String section) {
        browser.driver.get(baseURL + section);
        return this;
    }

    public void clickSimpleAlert() {
        simpleAlert.click();
    }
    public String getSimpleAlertLabelText() {
        return simpleAlertLabel.getText();
    }

    public void clickPromptAlert() {
        promptAlert.click();
    }

    public String getPromptAlertLabelText() {
        return promptAlertLabel.getText();
    }
    public void clickConfirmAlert() {
        confirmAlert.click();
    }

    public String getConfirmAlertLabelText() {
        return confirmAlertLabel.getText();
    }

    public void clickDelayedAlert() {
        delayedAlert.click();
    }

    public String getDelayedAlertLabelText() {
        return delayedAlertLabel.getText();
    }

    public BasicPages fillFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
        return this;
    }
    public BasicPages fillLastName(String lastName) {
        lastNameField.sendKeys(lastName);
        return this;
    }
    public BasicPages fillEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }
    public BasicPages chooseRandomSex() {
        browser.getRandomWebElement(sexRadioButtons).click();
        return this;
    }
    public BasicPages fillAge(int age) {
        ageField.clear();
        ageField.sendKeys(String.valueOf(age));
        return this;
    }
    public BasicPages chooseRandomExperience() {
        browser.getRandomWebElement(experienceRadioButtons).click();
        return this;
    }
    public BasicPages chooseProfession(String... profession) {
        for (String option : profession
        ) {
            switch (option) {
                case "Manual tester" -> browser.driver.findElement(By.id("gridCheckManulTester")).click();
                case "Automation Tester" -> browser.driver.findElement(By.id("gridCheckAutomationTester")).click();
                case "Other" -> browser.driver.findElement(By.id("gridCheckOther")).click();
            }
        }
        return this;
    }
    public BasicPages chooseRandomContinent() {
        Select select = new Select(selectContinentsField);
        int randomIndex = new Random().nextInt(1, select.getOptions().size());
        select.selectByIndex(randomIndex);
        return this;
    }
    public BasicPages chooseSeleniumCommands(String... command) {
        Select select = new Select(selectSeleniumCommandsField);
        for (String option : command
             ) {
            if (option.matches("Browser Commands|Navigation Commands|Switch Commands|Wait Commands|WebElement Commands")) {
                select.selectByVisibleText(option);
            }
        }
        return this;
    }
    public BasicPages uploadFile() {
        uploadFileInput.sendKeys("C:\\Users\\simon\\Documents\\Code\\Selenium\\testfile.txt");
        return this;
    }

    public BasicPages confirmForm() {
        submitButton.click();
        return this;
    }

    public String getSubmitMessageText() {
        return submitMessageField.getText();
    }
}
