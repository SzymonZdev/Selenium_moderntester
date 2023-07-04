package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.WidgetsPages;

import java.util.Date;

public class WidgetsTests extends BaseTest{

    //                              <<<<<<Accordion>>>>>>
    // 1 - Accordion test
    @Test
    public void accordion_all_sections_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("accordion.php");

        String[] sectionText = widgetsPages.getTextAllAccordionSections();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sectionText[0], "Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.");
        softAssert.assertEquals(sectionText[1], "Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In suscipit faucibus urna.");
        softAssert.assertEquals(sectionText[2], "Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.\n" +
                "List item one\n" +
                "List item two\n" +
                "List item three");
        softAssert.assertEquals(sectionText[3], "Cras dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia mauris vel est.");
        softAssert.assertAll();
    }

    //                              <<<<<<Autocomplete>>>>>>
    // 1 - Autocomplete test
    // Test inputs a specific value, chooses a random option,
    // then check if that option's value is in the search bar
    @Test
    public void autocomplete_search_test_for_specific_input() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("autocomplete.php");
        WebElement randomOption = widgetsPages
                .getRandomOptionFromAutocompleteAndClickIt("a");

        Assert.assertEquals(widgetsPages.getSearchBarText(), randomOption.getAttribute("textContent"));
    }

    //                              <<<<<<Datepicker>>>>>>
    // 1 - Datepicker test
    @Test
    public void select_several_dates_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("datepicker.php")
                .typeInDate("10/30/2018");

        Assert.assertEquals(widgetsPages.getDatepickerValue(), "10/30/2018");
        widgetsPages.goToPreviousMonth().clickOnDateOfMonth(25);

        Assert.assertEquals(widgetsPages.getDatepickerValue(), "09/25/2018");
        String currentDate = widgetsPages.getDatepickerValue();
        widgetsPages.typeInDate(currentDate);

        Assert.assertEquals(widgetsPages.getDatepickerValue(), currentDate);
        widgetsPages.goBackSeveralMonths(8).clickOnDateOfMonth(1);

        Assert.assertEquals(widgetsPages.getDatepickerValue(), "01/01/2018");
        widgetsPages.goToNextMonth().clickOnSameDay();

        Assert.assertEquals(widgetsPages.getDatepickerValue(), "02/01/2018");
        widgetsPages.goToTodaysDate();

        Assert.assertEquals(widgetsPages.getDatepickerValue(), widgetsPages.getTodaysDate());
    }
}
