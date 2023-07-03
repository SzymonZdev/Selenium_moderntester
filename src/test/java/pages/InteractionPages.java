package pages;

import helpers.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
}
