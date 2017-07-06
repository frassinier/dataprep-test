package org.talend.dataprep.qa.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration used to run a single story (as a unit test although it's in the main code part)
 */
@Configuration
@ComponentScan(basePackages = "org.talend.dataprep.qa")
public class SeleniumConfiguration {

    SeleniumConfiguration() {
        System.setProperty("java.awt.headless", "false");
    }

}
