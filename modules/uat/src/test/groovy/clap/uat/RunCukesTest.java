package clap.uat;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "src/test/resources/features", format = {"pretty", "html:build/cucumber-html-report", "json-pretty:build/cucumber-report.json"})
public class RunCukesTest {
}
