package org.talend.dataprep.qa.tests;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.talend.dataprep.qa.sso.SSOSteps;

/**
 *
 */
public class LoginStoryWrapper extends JUnitStory {

    private final SSOSteps ssoSteps;
    private DataPrepStory story;

    public LoginStoryWrapper(final DataPrepStory story, SSOSteps ssoSteps) {
        this.story = story;
        this.ssoSteps = ssoSteps;
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
        ssoSteps.whenILogIn("jimmy@dataprep.com", "jimmy");
        ssoSteps.thenIAmLoggedIn();
    }

    @AfterStory
    public void afterStory() {
        this.story.afterStory();
        // logout ?
    }
}
