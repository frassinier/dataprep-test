package org.talend.dataprep.qa.actions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.components.Actions;
import org.talend.dataprep.qa.components.Grid;
import org.talend.dataprep.qa.components.OnBoarding;
import org.talend.dataprep.qa.components.Recipe;
import org.talend.dataprep.qa.tests.DataPrepSteps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Component
public class FormatDateSteps extends DataPrepSteps {

    private static final String ACTION_TEXT = "Change date format...";

    private Grid grid;
    private Actions actions;
    private OnBoarding onBoarding;
    private Recipe recipe;

    private static boolean isValidFormat(String format, String value) throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(value);
        if (!value.equals(simpleDateFormat.format(date))) {
            date = null;
        }
        return date != null;
    }

    private String getFirstActiveCellValue(String columnName) {
        grid = new Grid(webDriver);
        grid.selectHeader(columnName);

        final List<WebElement> activeCells = grid.getActiveCells();
        assertThat(activeCells.size() > 1);
        return activeCells.get(0).getText();
    }

    @Given("column $columnName having $datePattern date pattern")
    public void columnHavingDatePattern(String columnName, String datePattern) throws ParseException {
        // Dismiss OnBoarding
        onBoarding = new OnBoarding(webDriver);
        onBoarding.dismiss();

        final String firstCellValue = getFirstActiveCellValue(columnName);
        assertThat(isValidFormat(datePattern, firstCellValue));
    }

    @When("I change $created date pattern to $datePattern")
    public void iChangeDatePatternTo(String columnName, String datePattern) {
        actions = new Actions(webDriver);
        actions.apply(ACTION_TEXT);
        actions.setOption("New format:", "custom");
        actions.setValue("Your format:", datePattern);
        actions.submit();
    }

    @Then("$columnName dates pattern should match $datePattern")
    public void datesShoudlHaveDatePattern(String columnName, String datePattern) throws ParseException {
        recipe = new Recipe(webDriver);
        recipe.contains(ACTION_TEXT, columnName);

        final String firstCellValue = getFirstActiveCellValue(columnName);
        assertThat(isValidFormat(datePattern, firstCellValue));
    }

    /**
     * @param webDriver the webDriver to set.
     */
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
