package org.talend.dataprep.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class TestConfiguration {

    @Bean(destroyMethod = "quit")
    public WebDriver driverProvider() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        return new ChromeDriver(options);
    }

}
