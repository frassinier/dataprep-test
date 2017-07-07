package org.talend.dataprep.qa.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talend.dataprep.qa.sso.SSOSteps;

/**
 *
 */
@Component(value = "eeBafDelegate")
public class EEBeforeAndAfterStoryDelegate extends BeforeAndAfterStoryDelegate {

    @Autowired
    private SSOSteps ssoSteps;

    @Override
    public void beforeStory() {
        super.beforeStory();

        ssoSteps.whenILogIn("jimmy@dataprep.com", "jimmy");
        ssoSteps.thenIAmLoggedIn();
        ssoSteps.dismissOnBoarding();
    }

    @Override
    public void afterStory() {
        super.afterStory();

        // logout ?
    }
}
