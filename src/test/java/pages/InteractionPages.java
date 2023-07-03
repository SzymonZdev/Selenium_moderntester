package pages;

import helpers.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class InteractionPages extends BasePage {
    @FindBy(css = "#draggable")
    WebElement draggableSquare;
    @FindBy(css = "#draggable.ui-draggable")
    WebElement droppableSquare;
    @FindBy(css = "#droppable")
    WebElement targetSquare;
    @FindBy(css = "#resizable")
    WebElement resizableSquare;
    @FindBy(css = ".ui-icon-gripsmall-diagonal-se")
    WebElement resizableSquareHandle;
    @FindBy(css = "#selectable")
    WebElement selectableSelect;
    @FindBy(css = "#feedback")
    WebElement selectableSelectResult;
    @FindBy(css = "#sortable")
    WebElement sortableUl;
    @FindBy(xpath = "//li[text() = 'Item 1']")
    WebElement firstSortableOption;

    private final Actions actions;
    public InteractionPages(Browser browser) {
        super(browser);
        actions = new Actions(driver);
        PageFactory.initElements(browser.driver, this);
    }

    public InteractionPages go(String section) {
        browser.driver.get(baseURL + section);
        return this;
    }

    public InteractionPages dragSquare(int xOffset, int yOffset) {
         actions.dragAndDropBy(draggableSquare, xOffset, yOffset);
         actions.perform();
         return this;
    }

    public boolean confirmRelativePositionOfDraggableSquare(int left, int top) {
        String stylePosition = draggableSquare.getAttribute("style");
        return stylePosition.matches(String.format("position: relative; left: %dpx; top: %dpx;",left, top));
    }
    public boolean confirmRelativePositionOfResizableSquare(double width, double height) {
        String stylePosition = resizableSquare.getAttribute("style");
        return stylePosition.matches(String.format("width: %.3fpx; height: %.3fpx;",width, height));
    }

    public InteractionPages dragAndDropSquare() {
        actions.dragAndDrop(droppableSquare, targetSquare).perform();
        return this;
    }

    public String getTargetSquareText() {
        return targetSquare.findElement(By.cssSelector("p")).getText();
    }

    public InteractionPages resizeWindow(int xPx, int yPx) {
        actions.clickAndHold(resizableSquareHandle)
                .moveByOffset(xPx, yPx)
                .release().perform();
        return this;
    }

    public InteractionPages selectOptionsByText(String...options) {
        actions.keyDown(Keys.CONTROL);
        for (String option : options
             ) {
            actions.click(selectableSelect.findElement(By.xpath("//li[text() = '" + option + "']")));
        }
        actions.perform();
        return this;
    }

    public String getSelectableResultsText() {
        return selectableSelectResult.getText();
    }

    public InteractionPages selectOptionsAccordingToList(List<Integer> randomList) {
        int firstElementLocationY = firstSortableOption.getLocation().y;

        for (int i = 0; i < randomList.size(); i++) {
            WebElement currentElement = sortableUl.findElement(By.xpath("//li[text() = 'Item " + randomList.get(i) + "']"));
            actions.clickAndHold(currentElement);
            actions.moveByOffset(0,  (firstElementLocationY + (i * 35)) - currentElement.getLocation().y);
            actions.perform();
        }



        return this;
    }

    public boolean sortableItemsAreCorrectlyOrdered(List<Integer> randomList) {
        List<WebElement> allItems = sortableUl.findElements(By.tagName("li"));
        for (int i = 0; i < randomList.size(); i++) {
            if (!allItems.get(i).getText().equals("Item " + randomList.get(i))) {
                return false;
            }
        }
        return true;
    }
}
