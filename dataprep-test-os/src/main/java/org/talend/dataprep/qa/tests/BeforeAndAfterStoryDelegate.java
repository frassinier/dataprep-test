package org.talend.dataprep.qa.tests;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * OS dataprep Before / After story delegate.
 *
 * It deals with web driver initialization / destruction before / after each story.
 *
 * @see DataPrepBeforeAndAfterStory
 */
@Component(value = "osBafDelegate")
public class BeforeAndAfterStoryDelegate {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Value("${run.base.url}")
    private String baseUrl;

    @Value("${run.timeout.sec:10}")
    private int timeoutInSec;

    @Value("${browser.driver.type:chrome}")
    private String driverType;

    @Value("${browser.driver.path:}")
    private String webDriverPath;

    @Value("${browser.driver.binary:}")
    private String driverBinary;

    @Value("${browser.headless:false}")
    private boolean headless;

    /** Current web driver instance. */
    private WebDriver currentWebDriver;

    /**
     * Init the new webDriver.
     */
    public void beforeStory() {
        // inject a new instance of the WebDriver into every steps before each story
        currentWebDriver = getWebDriver();
        final WebDriverWait webDriverWait = new WebDriverWait(currentWebDriver, timeoutInSec);

        // have the current web driver instance registered in the application context.
        ConfigurableListableBeanFactory bf = applicationContext.getBeanFactory();
        bf.registerResolvableDependency(WebDriver.class, currentWebDriver);
        bf.registerResolvableDependency(WebDriverWait.class, webDriverWait);

        // update all the Steps with the current web driver implementation
        final Map<String, DataPrepSteps> steps = applicationContext.getBeansOfType(DataPrepSteps.class);
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        for (Map.Entry<String, DataPrepSteps> step : steps.entrySet()) {
            beanFactory.autowireBeanProperties(step.getValue(), AutowireCapableBeanFactory.AUTOWIRE_NO, false);
            beanFactory.initializeBean(step.getValue(), step.getKey());
        }

        // open the base url
        currentWebDriver.get(baseUrl);
    }

    /**
     * Cleanup the webdriver.
     */
    public void afterStory() {
        currentWebDriver.quit();
    }

    /**
     * Instanciate a WebDriver depending on the configuration
     * 
     * @return A WebDriver
     */
    private WebDriver getWebDriver() {
        switch (driverType) {
            case "chrome":
                return getChromeDriver();
            case "firefox":
                return getFirefoxDriver();
        }
        return null;
    }

    /**
     * Instanciate a ChromeDriver with defined options
     * 
     * @return The chrome driver
     */
    private ChromeDriver getChromeDriver() {
        if (StringUtils.isNotBlank(webDriverPath)) {
            System.setProperty("webdriver.chrome.driver", webDriverPath);
        }

        final ChromeOptions options = new ChromeOptions();
        if (StringUtils.isNotBlank(driverBinary)) {
            options.setBinary(driverBinary);
        }
        if (headless) {
            options.addArguments("--headless");
        }

        return new ChromeDriver(options);
    }

    /**
     * Instanciate a FirefoxDriver with defined options
     * 
     * @return The firefox driver
     */
    private FirefoxDriver getFirefoxDriver() {
        if (StringUtils.isNotBlank(webDriverPath)) {
            System.setProperty("webdriver.gecko.driver", webDriverPath);
        }

        final FirefoxOptions options = new FirefoxOptions();
        if (StringUtils.isNotBlank(driverBinary)) {
            options.setBinary(driverBinary);
        }

        return new FirefoxDriver(options);
    }
}
