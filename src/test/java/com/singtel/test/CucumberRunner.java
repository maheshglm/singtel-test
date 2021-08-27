package com.singtel.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:testout/reports/cucumber.json",
        features = "src/test/resources/features",
        glue = "glue",
        monochrome = true,
        dryRun = false,
        tags = "@todo_management"
)
public class CucumberRunner extends BaseRunner {

}
