package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.WidgetsPages;


public class WidgetsTests extends BaseTest{
    @DataProvider(name = "modal-user-data")
    public Object[][] dataProvFunc() {
        return new Object[][] {
                {"John Doe", "john@doe.com", "password123"},
                {"Michael Cera", "michael@cera.com", "password"}
        };
    }

    //                              <<<<<<Accordion>>>>>>
    // - Accordion test
    @Test
    public void accordion_all_sections_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("accordion.php");

        String[] sectionText = widgetsPages.getTextAllAccordionSections();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sectionText[0],
                "\n" +
                        "          Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.\n" +
                        "        ");
        softAssert.assertEquals(sectionText[1],
                "\n" +
                        "          Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In suscipit faucibus urna. \n" +
                        "        ");
        softAssert.assertEquals(sectionText[2],
                "\n" +
                        "          Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui. \n" +
                        "          \n" +
                        "            List item one\n" +
                        "            List item two\n" +
                        "            List item three\n" +
                        "          \n" +
                        "        ");
        softAssert.assertEquals(sectionText[3], "\n" +
                "          Cras dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia mauris vel est. Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. \n" +
                "        ");
        softAssert.assertAll();
    }

    //                              <<<<<<Autocomplete>>>>>>
    // - Autocomplete test
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
    // - Datepicker test
    //    30.10.2018
    //    25.09.2018
    //    25.09.2018 (yes, again the same date)
    //    01.01.2018
    //    01.02.2018
    //    Today -> done long way round, clicking through calendar using regex to compare with expected
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

    //                              <<<<<<Menu>>>>>>
    // 1 - Menu test
    @Test
    public void click_on_value_in_menu_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("menu-item.php")
                .clickThroughMenuOptions("Music", "Jazz", "Modern");
    }

    //                              <<<<<<Modal dialog>>>>>>
    // - Modal dialog test with DataProvider
    @Test(dataProvider = "modal-user-data")
    public void fill_modal_dialog_form_test(String name, String email, String password) {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("modal-dialog.php")
                .openModalDialogForm()
                .fillModalForm(name, email, password)
                .submitModalForm();

        Assert.assertTrue(widgetsPages.checkForNewUser(name, email, password));
    }

    //                              <<<<<<Progress bar>>>>>>
    // - Progress Bar Test
    @Test
    public void wait_till_progress_bar_finishes_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("progressbar.php")
                .waitTillProgressBarFinishes();

        Assert.assertEquals(widgetsPages.getProgressBarText(), "Complete!");
    }

    //                              <<<<<<Selectable>>>>>>
    // - Select menus test
    @Test
    public void several_selects_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("selectmenu.php")
                .selectRandomSpeed()
                .selectFileByTextcontent("Some other file with a very long option text")
                .selectNumberByIndex(3)
                .selectRandomTitle();
    }

    //                              <<<<<<Slider>>>>>>
    // - Slider test
    @Test
    public void slider_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("slider.php")
                .moveSliderToValue(20);

        Assert.assertEquals(widgetsPages.getSliderValue(), 20);
        widgetsPages.moveSliderToValue(80);

        Assert.assertEquals(widgetsPages.getSliderValue(), 80);
        widgetsPages.moveSliderToValue(80);

        Assert.assertEquals(widgetsPages.getSliderValue(), 80);
        widgetsPages.moveSliderToValue(20);

        Assert.assertEquals(widgetsPages.getSliderValue(), 20);
        widgetsPages.moveSliderToValue(0);

        Assert.assertEquals(widgetsPages.getSliderValue(), 0);
    }

    //                              <<<<<<Tooltip>>>>>>
    // - Tooltip test
    @Test
    public void tooltip_test() {
        WidgetsPages widgetsPages = new WidgetsPages(browser);
        widgetsPages.go("tooltip.php");

        Assert.assertEquals(widgetsPages.getTooltipText(), "We ask for your age only for statistical purposes.");
    }
}
