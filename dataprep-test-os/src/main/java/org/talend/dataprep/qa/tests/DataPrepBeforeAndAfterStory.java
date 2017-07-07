package org.talend.dataprep.qa.tests;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 */
@Component
public class DataPrepBeforeAndAfterStory {

    @Resource(name = "osBafDelegate")
    private BeforeAndAfterStoryDelegate beforeAndAfterStoryImpl;

    @BeforeStory
    public void beforeStory() {
        beforeAndAfterStoryImpl.beforeStory();
    }

    @AfterStory
    public void afterStory() {
        beforeAndAfterStoryImpl.afterStory();
    }

    public void setBeforeAndAfterStoryImpl(BeforeAndAfterStoryDelegate beforeAndAfterStoryImpl) {
        this.beforeAndAfterStoryImpl = beforeAndAfterStoryImpl;
    }

}
