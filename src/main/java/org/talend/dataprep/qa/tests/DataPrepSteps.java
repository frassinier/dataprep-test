package org.talend.dataprep.qa.tests;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DataPrepSteps {

    @Autowired
    protected WebDriver webDriver;
}