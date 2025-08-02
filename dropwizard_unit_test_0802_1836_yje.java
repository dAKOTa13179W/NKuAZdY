// 代码生成时间: 2025-08-02 18:36:44
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * This class demonstrates how to create a unit test for a Dropwizard application.
 */
public class DropwizardUnitTest {

    // Rule to manage the lifecycle of the Dropwizard application for testing
    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
        new DropwizardAppRule<>(MyApplication.class, "config.yml");

    @Test
    public void testAppHealthCheck() {
        // Test the health check endpoint
        RULE.client().target("/health-check").request().get()
                .then().body("status", equalTo("ok"));
    }

    // Add more tests for different endpoints or application features here
    // ...
}
