package org.talend.dataprep.qa.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class Actions extends ComponentObject {

    public Actions(WebDriver driver) {
        super(driver);
    }

    public void apply(String actionText) {
        final WebElement action = wait.until(elementToBeClickable(By.xpath("//*[contains(text(), '" + actionText + "')]")));
        action.click();
    }

    public void setOption(String labelText, String optionText) {
        final List<WebElement> formGroups = this.getElements(By.cssSelector(".param"));
        formGroups.forEach(webElement -> {
            final String webElementText = webElement.getText();
            if (webElementText.contains(labelText)) {
                Select select = new Select(webElement.findElement(By.tagName("select")));
                select.selectByVisibleText(optionText);
            }
        });
    }

    public void setValue(String labelText, String optionText) {
        final List<WebElement> formGroups = this.getElements(By.cssSelector(".param"));
        formGroups.forEach(webElement -> {
            final String webElementText = webElement.getText();
            if (webElementText.contains(labelText)) {
                webElement.findElement(By.tagName("input")).sendKeys(optionText);
            }
        });
    }

    public void submit() {
        final WebElement btn = wait.until(elementToBeClickable(By.cssSelector(".param-buttons .btn")));
        btn.click();
    }
}
