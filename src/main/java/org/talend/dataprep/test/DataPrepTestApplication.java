package org.talend.dataprep.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.talend.dataprep")
public class DataPrepTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataPrepTestApplication.class, args);
    }

}
