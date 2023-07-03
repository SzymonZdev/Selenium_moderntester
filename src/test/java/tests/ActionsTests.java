package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InteractionPages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionsTests extends BaseTest {

    //                              <<<<<<Draggable>>>>>>
    // 1 - Drag test
    @Test
    public void drag_square_test() {
        InteractionPages interactionPages = new InteractionPages(browser);
        interactionPages.go("draggable.php")
                .dragSquare(150, 0)
                .dragSquare(0, 150)
                .dragSquare(-75, -75)
                .dragSquare(-75, 75);

        Assert.assertTrue(interactionPages.confirmRelativePositionOfDraggableSquare(0, 150));
    }

    //                              <<<<<<Droppable>>>>>>
    // 1 - Drop test
    @Test
    public void drop_to_target_test() {
        InteractionPages interactionPages = new InteractionPages(browser);
        interactionPages.go("droppable.php")
                .dragAndDropSquare();

        Assert.assertEquals(interactionPages.getTargetSquareText(), "Dropped!");
    }

    //                              <<<<<<Resizable>>>>>>
    // 1 - Resize test
    @Test
    public void resize_window_test() {
        InteractionPages interactionPages = new InteractionPages(browser);
        interactionPages.go("resizable.php")
                .resizeWindow(10, 0)
                .resizeWindow(0, 10)
                .resizeWindow(10, 10);

        Assert.assertTrue(interactionPages.confirmRelativePositionOfResizableSquare(235.667, 235.667));
    }

    //                              <<<<<<Selectable>>>>>>
    // 1 - Selectable test
    @Test
    public void selectable_options_test() {
        InteractionPages interactionPages = new InteractionPages(browser);
        interactionPages.go("selectable.php")
                .selectOptionsByText("Item 1", "Item 3", "Item 4");

        Assert.assertEquals(interactionPages.getSelectableResultsText(), "You've selected: #1 #3 #4.");
    }

    //                              <<<<<<Sortable>>>>>>
    // 1 - Sortable test
    @Test
    public void sortable_select_with_random_order_test() {
        InteractionPages interactionPages = new InteractionPages(browser);
        List<Integer> randomList = new ArrayList<>(List.of(1,2,3,4,5,6,7));
        Collections.shuffle(randomList);
        interactionPages.go("sortable.php")
                .selectOptionsAccordingToList(randomList);

        Assert.assertTrue(interactionPages.sortableItemsAreCorrectlyOrdered(randomList));
    }
}



