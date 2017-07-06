package org.talend.dataprep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.talend.dataprep.qa.tests.DataPrepStory;
import org.talend.dataprep.qa.tests.StoryRunner;

@SpringBootApplication
public class DataPrepTestApplication implements ApplicationRunner {

    @Autowired
    private StoryRunner storyRunner;

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
