package org.talend.dataprep.qa.tests;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Base class for the story runner.
 */
public abstract class StoryRunner {

    /** The stories to run. */
    @Autowired
    List<DataPrepStory> stories;

    /**
     * Run the stories one by one.
     * @throws Throwable you never know...
     */
    public abstract void runStories() throws Throwable;
}
