package org.talend.dataprep.qa.tests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StoryRunner {


    @Autowired
    private List<DataPrepStory> stories;

    public void runStories() throws Throwable {

        for (DataPrepStory story: stories) {
            story.run();
        }

    }
}
