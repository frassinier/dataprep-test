package org.talend.dataprep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.talend.dataprep.qa.tests.DefaultStoryRunner;

@SpringBootApplication
public class DataPrepTestApplication implements ApplicationRunner {

    @Autowired
    private DefaultStoryRunner storyRunner;

    public static void main(String[] args) {
        SpringApplication.run(DataPrepTestApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            storyRunner.runStories();
        } catch (Throwable throwable) {
            throw new Exception(throwable);
        }
    }
}
