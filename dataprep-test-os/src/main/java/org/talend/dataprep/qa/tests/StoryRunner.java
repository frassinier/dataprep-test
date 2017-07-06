package org.talend.dataprep.qa.tests;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 */
public abstract class StoryRunner {

    @Autowired
    List<DataPrepStory> stories;

    public abstract void runStories() throws Throwable;
}
