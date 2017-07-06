package org.talend.dataprep.qa.sso;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.tests.DataPrepSteps;

/**
 *
 */
@Component
public class SSOSteps extends DataPrepSteps {

    private static final Predicate<WebDriver> BE_ON_LOGIN_PAGE = d -> d.getTitle().startsWith("Talend - Login");
    private static final Function<? super WebDriver, Boolean> BE_ON_TDP_PAGE = d -> d.getTitle().startsWith("Data Preparation");


    private Map<String, User> users = new HashMap<>();


    @PostConstruct
    public void init() {
        users.put("Jimmy", new User("jimmy@dataprep.com", "jimmy"));
    }

    @When("I log in dataprep as $username using $password as password")
    public void whenILogIn(@Named("username") String username, String password) {

        // when
        assertTrue("The page should redirect to Talend login.", BE_ON_LOGIN_PAGE.test(webDriver));

        final WebElement usernameField = webDriver.findElement(By.id("lg-main-input-username"));
        final WebElement passwordField = webDriver.findElement(By.id("lg-main-input-password"));
        final WebElement submitField = webDriver.findElement(By.id("lg-main-button-login"));

        // when
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        submitField.click();

    }

    @Then("I am logged in dataprep")
    public void thenIAmLoggedIn() {
        final WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
        assertTrue("The page should redirect to TDP.",  webDriverWait.until(BE_ON_TDP_PAGE));
    }


    @Given("$userId is logged in")
    public void userIsLoggedIn(String userId) {
        final User user = users.get(userId);
        whenILogIn(user.getUsername(), user.getPassword());
        thenIAmLoggedIn();
        dismissOnBoarding();
    }

    @When("I dismiss on boarding")
    public void dismissOnBoarding() {
        final WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
        final WebElement onBoardingOverlay = webDriverWait.until(elementToBeClickable(By.cssSelector(".introjs-skipbutton")));
        onBoardingOverlay.click();
        webDriverWait.until((d) -> d.findElements(By.cssSelector(".introjs-overlay")).size() == 0);
    }


    private class User {

        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        /**
         * @return the Username
         */
        public String getUsername() {
            return username;
        }

        /**
         * @param username the username to set.
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * @return the Password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the password to set.
         */
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
