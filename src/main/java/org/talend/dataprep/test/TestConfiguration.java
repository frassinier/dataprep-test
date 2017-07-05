package org.talend.dataprep.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        return new ChromeDriver();
    }

}
