package org.talend.dataprep.qa.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class ComponentObject {

    private static final int DEFAULT_TIMEOUT = 120;

    protected WebDriver driver;
    protected WebDriverWait wait;

    public ComponentObject(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    /**
     * Get components from component selector
     *
     * @return List of WebElement
     */
    protected List<WebElement> getElements(By by) {
        return this.driver.findElements(by);
    }

    /**
     * Get component from its selector
     *
     * @return WebElement found with selector
     * @throws NotFoundException if no elements are found or if more than one element are found
     */
    protected WebElement getElement(By by) throws NotFoundException {
        List<WebElement> elements = this.getElements(by);
        if (elements.isEmpty()) {
            throw new NotFoundException(by.toString());
        }
        if (elements.size() > 1) {
            throw new NotFoundException("Too many WebElements found for " + by.toString());
        }
        return elements.get(0);
    }
}
