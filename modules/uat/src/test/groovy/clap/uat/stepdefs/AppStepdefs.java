package clap.uat.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;

import com.github.enr.clap.Clap;
import com.github.enr.clap.Clap.RunResult;
import com.github.enr.clap.util.ClasspathUtil;
import com.google.common.base.Joiner;
import com.google.inject.Module;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class AppStepdefs {
    
    private String sutName;
    
    private File sutHome;
    
    private String sutOutput;
    
    private int sutExitValue;

    @Given("^I am the user of app \"([^\"]*)\"$")
    public void I_am_the_user_of_app(String appName) {
        this.sutName = appName;
        File cc = ClasspathUtil.getClasspathForClass(AppStepdefs.class);
        File projectDir = cc.getParentFile().getParentFile().getParentFile();
        String sutHomePath = Joiner.on(File.separatorChar).join(projectDir.getAbsolutePath(), "src", "test", "data", "apps", this.sutName);
        this.sutHome = new File(sutHomePath);
    }

    @When("^I run app with \"([^\"]*)\" args$")
    public void I_run_app_with(String argsAsString) throws Exception {
        Module testModule = (Module) Class.forName("clap.uat.app."+this.sutName+".AcceptanceTestsModule").newInstance();
        String[] args = argsAsString.split("\\s");
        RunResult result = Clap.runReviewableApp(args, this.sutHome, testModule);
        this.sutExitValue = result.getExitValue();
        this.sutOutput = result.getOutput();
    }

    @Then("^it should exit with value \"([^\"]*)\"$")
    public void it_should_exit_with_value(int expectedExitValue) {
        assertEquals(expectedExitValue, this.sutExitValue);
    }

    @Then("^it should show \"([^\"]*)\"$")
    public void it_should_show(String expectedOutput) {
        assertEquals(expectedOutput, this.sutOutput);
    }

    @Then("^it should at least show \"([^\"]*)\"$")
    public void it_should_at_least_show(String expectedPieceOfOutput) {
        assertThat(this.sutOutput, containsString(expectedPieceOfOutput));
    }

    /*
     * used to test output if it is expected to show a path in os specific format.
     * warn: it replace every "/" char in expected output with the current os separator char.
     */
    @Then("^output line \"([^\"]*)\" should contain os path \"([^\"]*)\"$")
    public void output_line_should_show_os_path(int lineIndex, String expectedLineContent) {
        output_line_should_contain(lineIndex, expectedLineContent.replace('/', File.separatorChar));
    }
    
    @Then("^output line \"([^\"]*)\" should contain \"([^\"]*)\"$")
    public void output_line_should_contain(int lineIndex, String expectedLineContent) {
        String[] lines = this.sutOutput.split("\n");
        String actualLine = lines[lineIndex];
        assertTrue(actualLine.contains(expectedLineContent));
    }
    
    @Then("^output line \"([^\"]*)\" should be equal to \"([^\"]*)\"$")
    public void output_line_should_be_equal_to(int lineIndex, String expectedLineContent) {
        String[] lines = this.sutOutput.split("\n");
        String actualLine = lines[lineIndex];
        assertEquals(expectedLineContent, actualLine);
    }
}
