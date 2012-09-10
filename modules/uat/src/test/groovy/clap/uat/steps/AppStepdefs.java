package clap.uat.steps;

import static org.junit.Assert.*;

import java.io.File;

import clap.uat.app.hello.AcceptanceTestsModule;

import com.github.enr.clap.api.ClapApp;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.OutputAwareReporter;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.inject.Bindings;
import com.github.enr.clap.inject.ClapModule;
import com.github.enr.clap.util.ClasspathUtil;
import com.google.common.base.Joiner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class AppStepdefs {
    
    private String sutName;
    
    private File sutHome;
    
    private String sutOutput;

    @Given("^I am the user of app '([^\"]*)'$")
    public void I_am_the_user_of_app(String appName) {
        this.sutName = appName;
        File cc = ClasspathUtil.getClasspathForClass(AppStepdefs.class);
        File projectDir = cc.getParentFile().getParentFile().getParentFile();
        String sutHomePath = Joiner.on(File.separatorChar).join(projectDir.getAbsolutePath(), "src", "test", "data", "apps", this.sutName);
        this.sutHome = new File(sutHomePath);
    }

    @When("^I run app with \"([^\"]*)\" args$")
    public void I_run_app_with(String argsAsString) {
        // setup
        Injector injector = Guice.createInjector(Modules.override(new ClapModule()).with(new AcceptanceTestsModule()));
        EnvironmentHolder environment = injector.getInstance(EnvironmentHolder.class);
        environment.forceApplicationHome(this.sutHome);
        Reporter reporter = injector.getInstance(Reporter.class);
        ClapApp app = injector.getInstance(ClapApp.class);
        // method
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(argsAsString.split("\\s"));
        if (reporter instanceof OutputAwareReporter) {
            this.sutOutput = ((OutputAwareReporter) reporter).getOutput().trim();
        } else {
            this.sutOutput = null;
        }
    }

    @Then("^it should show \"([^\"]*)\"$")
    public void it_should_show(String expectedOutput) {
        assertEquals(expectedOutput, this.sutOutput);
    }

    /*
     * used to test output if it is expected to show a path in os specific format.
     * warn: it replace every "/" char in expected output with the current os separator char.
     */
    @Then("^output line '([^\"]*)' should contain os path '([^\"]*)'$")
    public void output_line_should_show_os_path(int lineIndex, String expectedLineContent) {
        output_line_should_contain(lineIndex, expectedLineContent.replace('/', File.separatorChar));
    }
    
    @Then("^output line '([^\"]*)' should contain '([^\"]*)'$")
    public void output_line_should_contain(int lineIndex, String expectedLineContent) {
        String[] lines = this.sutOutput.split("\n");
        String actualLine = lines[lineIndex];
        //assertEquals(expectedLineContent, actualLine);
        assertTrue(actualLine.contains(expectedLineContent));
    }
    
    @Then("^output line '([^\"]*)' should be equal to '([^\"]*)'$")
    public void output_line_should_be_equal_to(int lineIndex, String expectedLineContent) {
        String[] lines = this.sutOutput.split("\n");
        String actualLine = lines[lineIndex];
        //assertEquals(expectedLineContent, actualLine);
        assertTrue(actualLine.contains(expectedLineContent));
    }
}
