package org.talend.dataprep.test.service;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.talend.dataprep.test.Steps;

/**
 *
 */
@Steps
public class HugeServiceSteps {

    @Autowired
    private HugeService service;

    private int x;
    private int result;

    @Given("a variable x with value $value")
    public void givenXValue(@Named("value") int value) {
        this.x = value;
    }

    @When("I add x by $value")
    public void whenIAddXByY(@Named("value") int value) {
        this.result = service.add(x, value);
    }

    @Then("result should equal $value")
    public void thenXshouldBe(@Named("value") int value) {
        if (value != result) {
            throw new RuntimeException("result is " + result + ", but should be " + value);
        }
    }

}
