package org.talend.dataprep.qa.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

public class Recipe extends ComponentObject {

    private static final String ON = " on column ";

    private static final String ELLIPSIS = "...";

    private static final By STEP_SELECTOR = By.cssSelector(".recipe .trigger-container");

    public Recipe(WebDriver driver) {
        super(driver);
    }

    public ExpectedCondition<Boolean> contains(String actionName, String columnName) {
        if (actionName.endsWith(ELLIPSIS)) {
            actionName = actionName.replace(ELLIPSIS, "");
        }
        final String EXPECTED_STEP_TEXT = actionName + ON + columnName;
        return textToBePresentInElementLocated(STEP_SELECTOR, EXPECTED_STEP_TEXT);
    }
}
