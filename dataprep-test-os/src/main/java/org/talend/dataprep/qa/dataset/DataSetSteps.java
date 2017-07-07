package org.talend.dataprep.qa.dataset;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.tests.DataPrepSteps;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 *
 */
@Component
public class DataSetSteps extends DataPrepSteps {

    @When("I create a dataset from file using $filename")
    public void whenICreateDataSetFromFile(String fileName) throws AWTException, IOException {

        iListDataSets();

        final Path tempFile = Files.createTempFile("selenium-", '-'+fileName);

        try (final FileOutputStream fileOutputStream = new FileOutputStream(tempFile.toFile());
             final InputStream input = this.getClass().getResourceAsStream(fileName)) {
            IOUtils.copyLarge(input, fileOutputStream);
        }

        final WebElement addDataSetDropDown = wait.until(elementToBeClickable(By.id("datasets-list-actions-dataset:create")));
        addDataSetDropDown.click();

        final WebElement importLocalFile = wait.until(elementToBeClickable(By.id("dataset:create")));
        importLocalFile.click();

        final WebElement fileInput = webDriver.findElement(By.id("importDatasetFile"));
        fileInput.sendKeys(tempFile.toAbsolutePath().toString());

        Robot robot = new Robot();
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    @Then("dataset $datasetName is opened")
    public void datasetIsOpened(String datasetName) {
        wait.until((driver) -> driver.getTitle().endsWith(datasetName + " | Talend"));
    }

    @When("I list datasets")
    public void iListDataSets(){
        final WebElement userMenu = wait.until(elementToBeClickable(By.id("side-panel-nav-datasets")));
        userMenu.click();
    }

    /**
     * @param webDriver the webDriver to set.
     */
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
