package org.talend.dataprep.qa.sso;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.tests.DataPrepSteps;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * SSO related steps.
 */
@Component
public class SSOSteps extends DataPrepSteps {


    /** Make sure we're on the login page. */
    private static final Predicate<WebDriver> BE_ON_LOGIN_PAGE = d -> d.getTitle().startsWith("Talend - Login");
    /** Make sure we're on the TDP home page.*/
    private static final Function<? super WebDriver, Boolean> BE_ON_TDP_PAGE = d -> d.getTitle().startsWith("Data Preparation");

    /** Holds all registered users*/
    private Map<String, User> users = new HashMap<>();

    /**
     * Init all users.
     */
    @PostConstruct
    public void init() {
        users.put("Jimmy", new User("jimmy@dataprep.com", "jimmy"));
    }


    @When("I log in dataprep as $username using $password as password")
    public void whenILogIn(String username, String password) {

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
        assertTrue("The page should redirect to TDP.",  wait.until(BE_ON_TDP_PAGE));
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
        final WebElement onBoardingOverlay = wait.until(elementToBeClickable(By.cssSelector(".introjs-skipbutton")));
        onBoardingOverlay.click();
        wait.until((d) -> d.findElements(By.cssSelector(".introjs-overlay")).size() == 0);
    }


    private class User {

        private String username;
        private String password;

        User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        String getUsername() {
            return username;
        }

        String getPassword() {
            return password;
        }
    }
}
