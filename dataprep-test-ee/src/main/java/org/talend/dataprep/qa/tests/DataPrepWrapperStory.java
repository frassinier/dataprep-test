package org.talend.dataprep.qa.tests;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.InjectableStepsFactory;

/**
 *
 */
public class DataPrepWrapperStory extends JUnitStory {

    private DataPrepStory story;

    public DataPrepWrapperStory(final DataPrepStory story) {
        this.story = story;
    }

    @Override
    public Configuration configuration() {
        return this.story.configuration();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return this.story.stepsFactory();
    }

    @BeforeStory
    public void beforeStory() {
        this.story.beforeStory();

        // login
    }

    @AfterStory
    public void afterStory() {
        this.story.afterStory();

        // logout ?
    }
}
