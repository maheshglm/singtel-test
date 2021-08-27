package glue;

import com.singtel.test.Bootstrap;
import com.singtel.test.utils.DriverUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Hooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    private DriverUtils driverUtils = (DriverUtils) Bootstrap.getBean(DriverUtils.class);

    @Before
    public void setupDriver(Scenario scenario) {
        LOGGER.info("Executing Before hooks to configure driver");
        driverUtils.configWebDriver("chrome");
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                String filepath = "testout/screenshots/OnFailure.png";
                driverUtils.takeScreenshot(filepath);
                scenario.embed(filepath.getBytes(), "image/png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        driverUtils.quitWebDriver();
    }

}
