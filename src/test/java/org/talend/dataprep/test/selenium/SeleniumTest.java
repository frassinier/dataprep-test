package org.talend.dataprep.test.selenium;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SeleniumTest {


    private static final Function<? super WebDriver, Boolean> BE_ON_TDP_PAGE = d -> d.getTitle().startsWith("Data Preparation");
    private static final Predicate<WebDriver> BE_ON_LOGIN_PAGE = d -> d.getTitle().startsWith("Talend - Login");

    @Autowired
    private WebDriver webDriver;


    @Test
    public void shouldHaveWebDriver() {
        assertNotNull(webDriver);
    }

    @Test
    public void shouldLogin() {
        // given
        webDriver.get("http://dev.data-prep-ee.talend.lan:9999");

        // when
        assertTrue("The page should redirect to Talend login.", BE_ON_LOGIN_PAGE.test(webDriver));

        final WebElement usernameField = webDriver.findElement(By.id("lg-main-input-username"));
        final WebElement passwordField = webDriver.findElement(By.id("lg-main-input-password"));
        final WebElement submitField = webDriver.findElement(By.id("lg-main-button-login"));

        // when
        usernameField.sendKeys("jimmy@dataprep.com");
        passwordField.sendKeys("jimmy");
        submitField.click();

        // then
        final WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        assertTrue("The page should redirect to TDP.",  webDriverWait.until(BE_ON_TDP_PAGE));

        // then
        //webDriver.quit();
    }
}
