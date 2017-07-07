package org.talend.dataprep.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

    @Value("${base.url}")
    private String baseUrl;

    @Value("${timeout.sec}")
    private int timeoutInSec;

    /** Current web driver instance. */
    private WebDriver currentWebDriver;

    /**
     * Init the new webDriver.
     */
    public void beforeStory() {
        // inject a new instance of the WebDriver into every steps before each story
        currentWebDriver = new ChromeDriver();
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
}
