package clap.uat;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "src/test/resources/features", format = {"html:build/cucumber-report", "json:build/cucumber-json-report.json"})
public class RunCukesTest {
}
