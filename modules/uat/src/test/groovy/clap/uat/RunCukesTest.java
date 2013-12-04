package clap.uat;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", format = {"html:build/cucumber-report", "json:build/cucumber-json-report.json"})
public class RunCukesTest {
}
