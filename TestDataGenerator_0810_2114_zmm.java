// 代码生成时间: 2025-08-10 21:14:11
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewsBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/test-data")
public class TestDataResource {
    private final Random random;

    public TestDataResource(Random random) {
        this.random = random;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generateTestData() {
        String testData = String.format("{"id":%d, "name":"%s", "age":%d}",
                random.nextInt(10000),
                random.nextBoolean() ? "Alice" : "Bob",
                random.nextInt(100));
        return testData;
    }
}

public class TestDataGenerator extends Application<TestDataGeneratorConfiguration> {
    public static void main(String[] args) throws Exception {
        new TestDataGenerator().run(args);
    }

    @Override
    public void initialize(Bootstrap<TestDataGeneratorConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewsBundle<TestDataGeneratorConfiguration>() {
            @Override
            public void configure(ViewsConfigurer<TestDataGeneratorConfiguration> configurer) {
                configurer.addViewRenderer(new FreemarkerViewRenderer());
                configurer.addViewRenderer(new MustacheViewRenderer());
            }
        });
    }

    @Override
    public void run(TestDataGeneratorConfiguration configuration, Environment environment) {
        final Random random = new Random();
        environment.jersey().register(new TestDataResource(random));
    }
}

/*
 * Configuration class for Dropwizard application
 */
class TestDataGeneratorConfiguration extends Configuration {
    // Add any configuration properties here
}
