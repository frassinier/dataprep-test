package org.talend.dataprep.qa.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;

public class OnBoarding extends ComponentObject {

    private static final By OVERLAY = By.cssSelector(".introjs-overlay");
    private static final By SKIP_BUTTON = By.cssSelector(".introjs-skipbutton");

    public OnBoarding(WebDriver driver) {
        super(driver);
    }

    public void dismiss() {
        final WebElement skipButton = wait.until(elementToBeClickable(SKIP_BUTTON));
        skipButton.click();
        wait.until(invisibilityOf(getElement(OVERLAY)));
    }
}
