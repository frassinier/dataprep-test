package org.talend.dataprep.qa.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * EE stories runner.
 */
@Service
@Primary
public class EEStoryRunner extends StoryRunner {

    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    @Autowired
    private EEBeforeAndAfterStoryDelegate eeBafDelegate;

    @Autowired
    private DataPrepBeforeAndAfterStory baf;

    private List<DataPrepStory> osStories = new ArrayList<>();

    private List<DataPrepStory> eeStories = new ArrayList<>();

    /**
     * Sort the stories between OS and EE.s
     */
    @PostConstruct
    public void init() {
        stories.forEach(story -> {
            if (story instanceof EEDataPrepStory) {
                eeStories.add(story);
            } else {
                osStories.add(story);
            }
        });
    }

    @Override
    public void runStories() throws Throwable {

        // first run all EE stories
        for (DataPrepStory story : eeStories) {
            story.run();
        }

        // update the before and after story to add the login steps before each OS stories.
        baf.setDelegate(eeBafDelegate);

        for (DataPrepStory story : osStories) {
            story.run();
        }
    }
}
