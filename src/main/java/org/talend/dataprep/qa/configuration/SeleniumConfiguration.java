package org.talend.dataprep.qa.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ComponentScan(basePackages = "org.talend.dataprep.qa")
public class SeleniumConfiguration {
    SeleniumConfiguration() {
        System.setProperty("java.awt.headless", "false");
    }

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        return new ChromeDriver(options);
    }

}
