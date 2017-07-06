package org.talend.dataprep.qa.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.talend.dataprep.qa.sso.SSOSteps;

/**
 *
 */
@Service
@Primary
public class EEStoryRunner extends StoryRunner {

    @Autowired
    private SSOSteps ssoSteps;

    @Override
    public void runStories() throws Throwable {
        for (DataPrepStory story : stories) {
            if (story instanceof EEDataPrepStory) {
                story.run();
            } else {
                new LoginStoryWrapper(story, ssoSteps).run();
            }
        }
    }
}
