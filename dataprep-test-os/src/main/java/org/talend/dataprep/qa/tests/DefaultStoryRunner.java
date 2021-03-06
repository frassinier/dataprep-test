package org.talend.dataprep.qa.tests;

import org.springframework.stereotype.Service;

/**
 * Default implementation of the story runner.
 */
@Service
public class DefaultStoryRunner extends StoryRunner {


    @Override
    public void runStories() throws Throwable {
        for (DataPrepStory story: stories) {
            story.run();
        }
    }
}
