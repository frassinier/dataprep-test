package org.talend.dataprep.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 */
@Component(value = "osBafDelegate")
public class BeforeAndAfterStoryDelegate {

    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    @Value("${base.url}")
    protected String baseUrl;

    protected WebDriver localWebDriver;

    public void beforeStory() {
        // inject a new instance of the WebDriver into every steps before each story
        localWebDriver = new ChromeDriver();

        ConfigurableListableBeanFactory bf = applicationContext.getBeanFactory();
        bf.registerResolvableDependency(WebDriver.class, localWebDriver);

        final Map<String, DataPrepSteps> steps = applicationContext.getBeansOfType(DataPrepSteps.class);
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        for (Map.Entry<String, DataPrepSteps> step : steps.entrySet()) {
            beanFactory.autowireBeanProperties(step.getValue(), AutowireCapableBeanFactory.AUTOWIRE_NO, false);
            beanFactory.initializeBean(step.getValue(), step.getKey());
        }

        localWebDriver.get(baseUrl);
    }

    public void afterStory() {
        localWebDriver.quit();
    }
}
