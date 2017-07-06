package org.talend.dataprep.test.dataset;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.talend.dataprep.test.Steps;

import java.awt.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 *
 */
@Steps
public class DataSetSteps {

    @Autowired
    private WebDriver webDriver;

    public void removeExistingDataset(final String datasetName) {
        int index = 0;
        WebElement listItem;

        try {
            while (true) {
                listItem = webDriver.findElement(By.id("datasets-list-" + index + "-title"));
                if (listItem.getText().equals(datasetName)) {
                    final Actions action = new Actions(webDriver);
                    final WebElement removeButton = webDriver.findElement(By.id("datasets-list-" + index + "-dataset:remove"));
                    action.moveToElement(removeButton).build().perform();

                    removeButton.click();

                    final WebDriverWait wait = new WebDriverWait(webDriver, 10);
                    final WebElement validButton = wait.until((driver) -> {
                        final WebElement element = webDriver.switchTo().activeElement().findElement(By.cssSelector(".modal-primary-button"));
                        return element != null && element.isEnabled() ? element : null;
                    });
                    validButton.click();

                    final int selectedIndex = index;
                    wait.until(driver ->
                            !driver
                                    .findElement(By.id("datasets-list-" + selectedIndex + "-title"))
                                    .getText()
                                    .equals(datasetName)

                    );

                    final WebElement toaster = wait.until(elementToBeClickable(By.cssSelector(".toast-success")));
                    toaster.click();
                    wait.until(invisibilityOfElementLocated(By.cssSelector(".toast-success")));

                    break;
                }
                index++;
            }
        } catch (final NoSuchElementException e) {}
    }

    @When("I create a dataset from file using $filename")
    public void whenICreateDataSetFromFile(String fileName) throws AWTException {
        iListDataSets();

        final WebDriverWait wait = new WebDriverWait(webDriver, 30);

        wait.until(visibilityOfElementLocated(By.id("datasets-list-actions-dataset:create")));
        final String datasetName = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(fileName, "/"),
                "."
        );
        removeExistingDataset(datasetName);

        final WebElement addDataSetDropDown = wait.until(elementToBeClickable(By.id("datasets-list-actions-dataset:create")));
        addDataSetDropDown.click();

        final WebElement importLocalFile = wait.until(elementToBeClickable(By.id("dataset:create")));
        importLocalFile.click();

        final WebElement fileInput = webDriver.findElement(By.id("importDatasetFile"));
        fileInput.sendKeys(fileName);
    }

    @Then("dataset $datasetName is opened")
    public void datasetIsOpened(String datasetName) {
        final WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until((driver) -> driver.getTitle().equals(datasetName + " | Talend"));
    }

    @When("I list datasets")
    public void iListDataSets(){
        final WebDriverWait wait = new WebDriverWait(webDriver, 30);
        final WebElement userMenu = wait.until(elementToBeClickable(By.id("side-panel-nav-datasets")));
        userMenu.click();
    }
}
