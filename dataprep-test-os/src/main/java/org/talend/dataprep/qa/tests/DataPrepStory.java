package org.talend.dataprep.qa.tests;

import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;

import java.util.Arrays;
import java.util.Map;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.io.CasePreservingResolver;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.talend.dataprep.qa.configuration.SeleniumConfiguration;

/**
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SeleniumConfiguration.class})
public abstract class DataPrepStory extends JUnitStory {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Value("${base.url}")
    private String baseUrl;

    private WebDriver localWebDriver;


    public DataPrepStory() {
        Embedder embedder = new Embedder();
        embedder.useEmbedderControls(embedderControls());
        embedder.useMetaFilters(Arrays.asList("+author *", "theme *","-skip"));
        useEmbedder(embedder);
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryPathResolver(storyPathResolver())
                .useStoryLoader(storyLoader())
                .useStoryReporterBuilder(storyReporterBuilder())
                .useParameterControls(parameterControls());
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    private EmbedderControls embedderControls() {
        return new EmbedderControls().doIgnoreFailureInView(true);
    }

    private ParameterControls parameterControls() {
        return new ParameterControls().useDelimiterNamedParameters(true);
    }

    private StoryPathResolver storyPathResolver() {
        return new CasePreservingResolver();
    }

    private StoryLoader storyLoader() {
        return new LoadFromClasspath();
    }

    private StoryReporterBuilder storyReporterBuilder() {
        return new StoryReporterBuilder() //
                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass())) //
                .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName()) //
                .withFailureTrace(true) //
                .withDefaultFormats() //
                .withFormats(ANSI_CONSOLE, TXT, HTML);
    }

    @BeforeStory
    public void beforeStory() {

        // inject a new instance of the WebDriver into every steps before each story
        localWebDriver = new ChromeDriver();

        ConfigurableListableBeanFactory bf = applicationContext.getBeanFactory();
        bf.registerResolvableDependency(WebDriver.class, localWebDriver);

        final Map<String, DataPrepSteps> steps = applicationContext.getBeansOfType(DataPrepSteps.class);
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        for (Map.Entry<String, DataPrepSteps> step : steps.entrySet()) {
            beanFactory.autowireBeanProperties(step.getValue(), AutowireCapableBeanFactory.AUTOWIRE_NO, false);
            beanFactory.initializeBean(step.getValue(), step.getKey());
        }

        localWebDriver.get(baseUrl);
    }

    @AfterStory
    public void afterStory() {
        localWebDriver.quit();
    }
}
