// 代码生成时间: 2025-08-23 15:15:28
package com.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class DropwizardApplicationWithTesting extends JerseyTest {

    @Path("/test")
    public static class TestResource {

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String sayHello() {
            return "Hello, World!";
        }
    }

    public static class MyApplication extends Application<MyConfiguration> {

        @Override
        public void initialize(Bootstrap<MyConfiguration> bootstrap) {
            // nothing to do here
        }

        @Override
        public void run(MyConfiguration configuration, Environment environment) throws Exception {
            environment.jersey().register(new TestResource());
        }
    }

    public static class MyConfiguration extends io.dropwizard.Configuration {
        // Configuration class
    }

    @ClassRule
    public static final DropwizardAppRule<MyConfiguration> RULE =
            new DropwizardAppRule<>(MyApplication.class, ResourceHelpers.resourceFilePath("config.yaml"));

    @Override
    protected junit.framework.TestCase createTest() {
        return new junit.framework.TestCase() {
            @Override
            protected void runTest() throws Throwable {
                // nothing to do here
            }
        };
    }

    @Test
    public void testSayHello() {
        // Given
        String path = "/test";

        // When
        String response = target(path).request().get(String.class);

        // Then
        assertEquals("Hello, World!", response);
    }
}
