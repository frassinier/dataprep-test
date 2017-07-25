package org.talend.dataprep.qa.actions;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.tests.DataPrepSteps;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

/**
 *
 */
@Component
public class UpperCaseSteps extends DataPrepSteps {

    private static final String ACTION_TEXT = "Change to upper case";

    private static final By ACTION_SELECTOR = By.xpath("//*[contains(text(), '" + ACTION_TEXT + "')]");

    private static final By STEP_SELECTOR = By.cssSelector(".recipe .trigger-container");

    @When("I dismiss on boarding")
    public void dismissOnBoarding() {
        final WebElement onBoardingOverlay = wait.until(elementToBeClickable(By.cssSelector(".introjs-skipbutton")));
        onBoardingOverlay.click();
        wait.until((d) -> d.findElements(By.cssSelector(".introjs-overlay")).size() == 0);
    }

    @When("I set column $columnName to upper case")
    public void whenISetColumnToUpperCase(String columnName) {
        // Remove onboarding
        dismissOnBoarding();

        // Set column active
        final WebElement columnHeader = wait.until(elementToBeClickable(By.cssSelector(".grid-header-title[title='" + columnName + "']")));
        columnHeader.click();

        // Select upper case action
        final WebElement uppercaseAction = wait.until(elementToBeClickable(ACTION_SELECTOR));
        uppercaseAction.click();
    }

    @Then("change to upper case step appears in recipe for $columnName")
    public void changeToUpperCaseStepAppearsInRecipeFor(String columnName) {
        final String STEP_TEXT = "1 " + ACTION_TEXT + " on column " + columnName;
        wait.until(textToBePresentInElementLocated(STEP_SELECTOR, STEP_TEXT));
    }

    /**
     * @param webDriver the webDriver to set.
     */
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
