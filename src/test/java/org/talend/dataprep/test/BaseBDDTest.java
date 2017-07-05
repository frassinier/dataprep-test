package org.talend.dataprep.test;

import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;

import java.util.Arrays;

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
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseBDDTest extends JUnitStory {

    @Autowired
    private ApplicationContext applicationContext;

    public BaseBDDTest() {
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
        return new EmbedderControls()
                .doIgnoreFailureInView(true);
    }

    private ParameterControls parameterControls() {
        return new ParameterControls()
                .useDelimiterNamedParameters(true);
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
                .withPathResolver(new ResolveToPackagedName()) //
                .withFailureTrace(true) //
                .withDefaultFormats() //
                .withFormats(ANSI_CONSOLE, TXT, HTML);
    }

}
