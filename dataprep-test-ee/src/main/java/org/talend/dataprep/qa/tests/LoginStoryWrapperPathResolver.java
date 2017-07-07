package org.talend.dataprep.qa.tests;

import org.jbehave.core.Embeddable;
import org.jbehave.core.io.CasePreservingResolver;

/**
 *
 */
public class LoginStoryWrapperPathResolver extends CasePreservingResolver {

    private DataPrepStory story;

    public LoginStoryWrapperPathResolver(DataPrepStory story) {
        super();
        this.story = story;
    }

    @Override
    protected String resolveName(Class<? extends Embeddable> embeddableClass) {
        return story.getClass().getSimpleName();
    }

    @Override
    protected String resolveDirectory(Class<? extends Embeddable> embeddableClass) {
        return super.resolveDirectory(story.getClass());
    }
}
