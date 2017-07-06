package org.talend.dataprep.qa.tests;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Primary
public class EEStoryRunner extends StoryRunner {

    @Override
    public void runStories() throws Throwable {
        for (DataPrepStory story : stories) {
            if (story instanceof EEDataPrepStory) {
                story.run();
            } else {
                new DataPrepWrapperStory(story).run();
            }
        }
    }
}
