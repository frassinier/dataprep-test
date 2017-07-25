package org.talend.dataprep.qa.actions;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.components.Actions;
import org.talend.dataprep.qa.components.Grid;
import org.talend.dataprep.qa.components.OnBoarding;
import org.talend.dataprep.qa.components.Recipe;
import org.talend.dataprep.qa.tests.DataPrepSteps;

/**
 *
 */
@Component
public class UpperCaseSteps extends DataPrepSteps {

    private static final String ACTION_TEXT = "Change to upper case";

    private Actions actions;
    private Grid grid;
    private OnBoarding onBoarding;
    private Recipe recipe;

    @When("I set column $columnName to upper case")
    public void whenISetColumnToUpperCase(String columnName) {
        // Dismiss OnBoarding
        onBoarding = new OnBoarding(webDriver);
        onBoarding.dismiss();

        // Set column active
        grid = new Grid(webDriver);
        grid.selectHeader(columnName);

        // Select upper case action
        actions = new Actions(webDriver);
        actions.apply(ACTION_TEXT);
    }

    @Then("change to upper case step appears in recipe for $columnName")
    public void changeToUpperCaseStepAppearsInRecipeFor(String columnName) {
        recipe = new Recipe(webDriver);
        wait.until(recipe.contains(ACTION_TEXT, columnName));
    }

    /**
     * @param webDriver the webDriver to set.
     */
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
