package org.talend.dataprep.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DataPrepSteps {

    @Autowired(required = false)
    protected WebDriver webDriver;

    @Autowired(required = false)
    protected WebDriverWait wait;

}