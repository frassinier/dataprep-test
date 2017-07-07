package org.talend.dataprep.qa.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Primary
public class EEStoryRunner extends StoryRunner {

    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    private List<DataPrepStory> osStories = new ArrayList<>();

    private List<DataPrepStory> eeStories = new ArrayList<>();

    @Autowired
    private EEBeforeAndAfterStoryDelegate eeBafDelegate;

    @Autowired
    private DataPrepBeforeAndAfterStory baf;

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
        for (DataPrepStory story : eeStories) {
            story.run();
        }

//        final DataPrepBeforeAndAfterStory eeBafStory = new EEDataPrepBeforeAndAfterStory();
//        final AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
//        beanFactory.autowireBeanProperties(eeBafStory, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
//        beanFactory.initializeBean(eeBafStory, bafStory.getKey());

//        ConfigurableListableBeanFactory bf = applicationContext.getBeanFactory();
//        bf.registerResolvableDependency(BeforeAndAfterStoryDelegate.class, eeBafDelegate);

//        final AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
//        final Map<String, BeforeAndAfterStoryDelegate> bafStories = applicationContext.getBeansOfType(BeforeAndAfterStoryDelegate.class);
//        for (Map.Entry<String, BeforeAndAfterStoryDelegate> bafStory : bafStories.entrySet()) {
//            beanFactory.autowireBeanProperties(bafStory.getValue(), AutowireCapableBeanFactory.AUTOWIRE_NO, false);
//            beanFactory.initializeBean(bafStory.getValue(), bafStory.getKey());
//        }

        baf.setBeforeAndAfterStoryImpl(eeBafDelegate);

        for (DataPrepStory story : osStories) {
            story.run();
        }
    }
}
