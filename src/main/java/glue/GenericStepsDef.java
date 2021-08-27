package glue;

import com.singtel.test.Bootstrap;
import com.singtel.test.steps.GenericSteps;
import cucumber.api.java.en.When;

public class GenericStepsDef {

    private GenericSteps genericSteps = (GenericSteps) Bootstrap.getBean(GenericSteps.class);

    @When("I pause for {int} seconds")
    public void pause(int seconds) {
        genericSteps.pauseExecution(seconds);
    }
}
