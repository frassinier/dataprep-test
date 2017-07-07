package org.talend.dataprep.qa.tests;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DataPrep before and after story place holder.
 *
 * Since JBehave cannot handle a single before/after story per story, this class serves as a single point of entry then
 * delegate the code to a... delegate !
 *
 * Just call DataPrepBeforeAndAfterStory{@link #setDelegate(BeforeAndAfterStoryDelegate)} to change the behavior.
 */
 @Component
public class DataPrepBeforeAndAfterStory {

    /** OS BAF delegate */
    @Resource(name = "osBafDelegate")
    private BeforeAndAfterStoryDelegate delegate;

    /**
     * Global and single before story method.
     */
    @BeforeStory
    public void beforeStory() {
        delegate.beforeStory();
    }

    /**
     * Global and single before story method.
     */
    @AfterStory
    public void afterStory() {
        delegate.afterStory();
    }

    /**
     * Set the BeforeAndAfterStory delegate.
     * @param delegate the BeforeAndAfterStory delegate to use.
     */
    public void setDelegate(BeforeAndAfterStoryDelegate delegate) {
        this.delegate = delegate;
    }

}
