package pages;

import helpers.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.KeyInput;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WidgetsPages extends BasePage {
    @FindBy(css = "#accordion")
    WebElement allAccordionDiv;
    @FindBy(css = ".ui-autocomplete-input")
    WebElement autocompleteSearchBar;
    @FindBy(css = "#datepicker")
    WebElement datepicker;
    @FindBy(css = "a.ui-datepicker-prev")
    WebElement datepickerPreviousMonth;
    @FindBy(css = "a.ui-datepicker-next")
    WebElement datepickerNextMonth;
    @FindBy(css = ".ui-datepicker-title")
    WebElement datepickerHeader;
    @FindBy(css = "tr td")
    List<WebElement> datepickerDays;
    @FindBy(css = "ul#menu")
    WebElement menu;
    @FindBy(css = "#create-user")
    WebElement createNewUserButton;
    @FindBy(css = "#name")
    WebElement modalName;
    @FindBy(css = "#email")
    WebElement modalEmail;
    @FindBy(css = "#password")
    WebElement modalPassword;
    @FindBy(xpath = "//button[text() ='Create an account']")
    WebElement modalCreateAccountButton;
    @FindBy(css = "#users tbody")
    WebElement existingUsersTable;
    @FindBy(css = "#progressbar")
    WebElement progressBar;
    @FindBy(css = "ul#speed-menu")
    WebElement speedSelect;
    @FindBy(css = "#speed-button")
    WebElement speedSelectSpan;
    @FindBy(css = "ul#files-menu")
    WebElement fileSelect;
    @FindBy(css = "#files-button")
    WebElement fileSelectButton;
    @FindBy(css = "ul#number-menu")
    WebElement numberSelect;
    @FindBy(css = "#number-button")
    WebElement numberSelectButton;
    @FindBy(css = "ul#salutation-menu")
    WebElement salutationSelect;
    @FindBy(css = "#salutation-button")
    WebElement salutationSelectButton;
    @FindBy(css = "#custom-handle")
    WebElement slider;
    @FindBy(css = "div.ui-tooltip")
    WebElement tooltip;
    @FindBy(css = "#age")
    WebElement tooltipInput;


    public WidgetsPages(Browser browser) {
        super(browser);
        PageFactory.initElements(browser.driver, this);
    }

    public WidgetsPages go(String section) {
        browser.driver.get(baseURL + section);
        return this;
    }

    public String[] getTextAllAccordionSections() {
        List<WebElement> allSections = allAccordionDiv.findElements(By.cssSelector("h3.ui-accordion-header"));
        String[] allText = new String[allSections.size()];
        int index = 0;
        Actions actions = new Actions(driver);
        for (WebElement section : allSections
             ) {
            if (index != 0) {
                actions.click(section).perform();
            }
            wait.until(ExpectedConditions.attributeToBe(section, "aria-selected", "true"));
            allText[index++] = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("h3.ui-accordion-header-active+div")))).getAttribute("textContent");
        }
        return allText;
    }

    public WebElement getRandomOptionFromAutocompleteAndClickIt(String input){
        Actions actions = new Actions(driver);
        actions.clickAndHold(autocompleteSearchBar).sendKeys("a").perform();
        List<WebElement> autocompleteOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector("li.ui-menu-item"))));
        WebElement randomElement = browser.getRandomWebElement(autocompleteOptions);
        randomElement.click();
        return randomElement;
    }

    public String getSearchBarText() {
        return autocompleteSearchBar.getAttribute("value");
    }
    public String getDatepickerValue() {
        return datepicker.getAttribute("value");
    }

    public WidgetsPages typeInDate(String date) {
        datepicker.clear();
        datepicker.sendKeys(date);
        return this;
    }

    public WidgetsPages goToPreviousMonth() {
        datepicker.click();
        wait.until(ExpectedConditions.visibilityOf(datepickerPreviousMonth)).click();
        return this;
    }

    public WidgetsPages clickOnDateOfMonth(int i) {
        datepicker.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(datepickerDays))
                .stream()
                .filter(e -> e.getAttribute("textContent").equals(String.valueOf(i)))
                .findFirst()
                .get().click();
        return this;
    }

    public WidgetsPages goBackSeveralMonths(int i) {
        datepicker.click();
        for (int j = i; j != 0; j--) {
            wait.until(ExpectedConditions.visibilityOf(datepickerPreviousMonth)).click();
        }
        return this;
    }

    public WidgetsPages goToNextMonth() {
        datepicker.click();
        wait.until(ExpectedConditions.visibilityOf(datepickerNextMonth)).click();
        return this;
    }

    public WidgetsPages clickOnSameDay() {
        String currentDate = getDatepickerValue();
        String[] str = currentDate.split("/");
        int day = Integer.parseInt(str[1]);
        return clickOnDateOfMonth(day);
    }

    public WidgetsPages goToTodaysDate() {
        String currentDate = getTodaysDate();
        String[] currentDateStr = currentDate.split("/");
        int currentMonth = Integer.parseInt(currentDateStr[0]);
        int currentDay = Integer.parseInt(currentDateStr[1]);
        int currentYear = Integer.parseInt(currentDateStr[2]);
        //typeInDate(currentDate);
        while (!headerMatchesDate(currentMonth, currentYear)) {
            goToNextMonth();
        }
        return clickOnDateOfMonth(currentDay);
    }

    private boolean headerMatchesDate(int currentMonth, int currentYear) {
        Map<Integer, String> months = new HashMap<>();
        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");
        months.put(4, "April");
        months.put(5, "May");
        months.put(6, "June");
        months.put(7, "July");
        months.put(8, "August");
        months.put(9, "September");
        months.put(10, "October");
        months.put(11, "November");
        months.put(12, "December");
        String expectedHeader = months.get(currentMonth) + currentYear;

        return getDatepickerHeaderValue().replaceAll("[\\s|\\u00A0]+", "").contentEquals(expectedHeader);
    }

    public String getTodaysDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
    }
    public String getDatepickerHeaderValue() {
        return datepickerHeader.getAttribute("textContent");
    }

    public WidgetsPages clickThroughMenuOptions(String... options) {
        WebElement currentMenu = menu;
        for (String option: options
             ) {
            WebElement menuOption = currentMenu.findElements(By.cssSelector(".ui-menu-item"))
                    .stream().filter(e -> e.getAttribute("textContent").contains(option))
                    .findFirst().get();
            wait.until(ExpectedConditions.elementToBeClickable(menuOption)).click();
            currentMenu = menuOption;
        }
        return this;
    }

    public WidgetsPages openModalDialogForm() {
        createNewUserButton.click();
        return this;
    }

    public WidgetsPages fillModalForm(String name, String email, String password) {
        modalName.clear();
        modalName.sendKeys(name);
        modalEmail.clear();
        modalEmail.sendKeys(email);
        modalPassword.clear();
        modalPassword.sendKeys(password);
        return this;
    }

    public WidgetsPages submitModalForm() {
        modalCreateAccountButton.click();
        return this;
    }

    public boolean checkForNewUser(String name, String email, String password) {
        WebElement lastRow = existingUsersTable.findElement(By.cssSelector("tr:last-of-type"));
        List<WebElement> columns = lastRow.findElements(By.cssSelector("td"));
        boolean newUserPresent = columns.get(0).getText().equals(name);
        if (!columns.get(1).getText().equals(email)) {
                newUserPresent = false;
            }
        if (!columns.get(2).getText().equals(password)) {
            newUserPresent = false;
        }
        return newUserPresent;
    }

    public WidgetsPages waitTillProgressBarFinishes() {
        // Overwriting to make timeout longer
        wait = new WebDriverWait(driver, 25);
        wait.until(ExpectedConditions.attributeToBe(progressBar, "innerText", "Complete!"));
        return this;
    }

    public String getProgressBarText() {
        return progressBar.getText();
    }

    public WidgetsPages selectRandomSpeed() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("select")));
        speedSelectSpan.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(speedSelect.findElements(By.tagName("li"))));
        browser.getRandomWebElement(speedSelect.findElements(By.tagName("li"))).click();

        return this;
    }

    public WidgetsPages selectFileByTextcontent(String textContent) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("select")));
        fileSelectButton.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(fileSelect.findElements(By.tagName("li"))))
                .stream().filter(e -> e.getAttribute("textContent").equals(textContent))
                .findFirst().get().click();

        return this;
    }

    public WidgetsPages selectNumberByIndex(int index) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("select")));
        numberSelectButton.click();
        List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElements(numberSelect.findElements(By.tagName("li"))));
        list.get(index).click();

        return this;
    }

    public WidgetsPages selectRandomTitle() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("select")));
        salutationSelectButton.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(salutationSelect.findElements(By.tagName("li"))));
        browser.getRandomWebElement(salutationSelect.findElements(By.cssSelector("li:not(.ui-state-disabled)"))).click();

        return this;
    }

    public WidgetsPages moveSliderToValue(int intendedValue) {
        int currentSliderValue = Integer.parseInt(slider.getAttribute("textContent"));
        if (currentSliderValue > intendedValue) {
            slider.click();
            int difference = currentSliderValue - intendedValue;
            for (int i = 0; i < difference; i++) {
                slider.sendKeys(Keys.LEFT);
            }
        } else if (currentSliderValue < intendedValue) {
            slider.click();
            int difference = intendedValue - currentSliderValue;
            for (int i = 0; i < difference; i++) {
                slider.sendKeys(Keys.RIGHT);
            }
        }

        return this;
    }

    public int getSliderValue() {
        return Integer.parseInt(slider.getAttribute("textContent"));
    }

    public String getTooltipText() {
        Actions actions = new Actions(driver);
        actions.moveToElement(tooltipInput).perform();

        return tooltip.getText();
    }
}
