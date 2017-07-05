package org.talend.dataprep.test.dataset;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.talend.dataprep.test.Steps;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 *
 */
@Steps
public class DataSetSteps {

    @Autowired
    private WebDriver webDriver;


    @When("I create a dataset from file using $filename")
    public void whenICreateDataSetFromFile(String fileName) {
        iListDataSets();

        final WebDriverWait wait = new WebDriverWait(webDriver, 30);

        final WebElement addDataSetDropDown = wait.until(elementToBeClickable(By.id("datasets-list-actions-dataset:create")));
        addDataSetDropDown.click();

        final WebElement importLocalFile = wait.until(elementToBeClickable(By.id("dataset:create")));
        importLocalFile.click();

        final WebElement fileInput = webDriver.findElement(By.id("importDatasetFile"));
        fileInput.click();
        fileInput.sendKeys(fileName);
    }

    @Then("dataset $datasetName is opened")
    public void datasetIsOpened(String datasetName) {
        final WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until((driver) -> driver.getTitle().equals(datasetName + " | Talend"));
    }

    @When("I list datasets")
    public void iListDataSets(){
        final WebDriverWait wait = new WebDriverWait(webDriver, 30);
        final WebElement userMenu = wait.until(elementToBeClickable(By.id("side-panel-nav-datasets")));
        userMenu.click();
    }
}
