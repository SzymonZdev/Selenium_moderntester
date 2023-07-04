package pages;

import helpers.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            allText[index++] = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("h3.ui-accordion-header-active+div")))).getText();
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

        StringBuilder expectedHeader = new StringBuilder()
                .append(months.get(currentMonth))
                .append(" ")
                .append(currentYear);

        System.out.println(getDatepickerHeaderValue().);
        System.out.println(expectedHeader);

        return getDatepickerHeaderValue().contentEquals(expectedHeader);
    }

    public String getTodaysDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
    }
    public String getDatepickerHeaderValue() {
        return datepickerHeader.getAttribute("textContent");
    }
}
