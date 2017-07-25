package org.talend.dataprep.qa.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class Grid extends ComponentObject {

    private static final By ACTIVE_CELLS = By.cssSelector(".slick-cell.selected");

    public Grid(WebDriver driver) {
        super(driver);
    }

    public void selectHeader(String columnName) {
        final WebElement columnHeader = wait.until(elementToBeClickable(By.cssSelector(".grid-header-title[title='" + columnName + "']")));
        columnHeader.click();
    }

    public List<WebElement> getActiveCells() {
        return this.getElements(ACTIVE_CELLS);
    }
}

