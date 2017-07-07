package org.talend.dataprep.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for all data prep steps.
 */
public abstract class DataPrepSteps {

    /** the web driver to use. */
    @Autowired(required = false) // required false is important as the web driver is injected by DataPrepBeforeStory#beforeStory
    protected WebDriver webDriver;

    /** The web driver wait to use. */
    @Autowired(required = false)  // required false is important as the web driver is injected by DataPrepBeforeStory#beforeStory
    protected WebDriverWait wait;

}