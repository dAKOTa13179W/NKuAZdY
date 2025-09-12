// 代码生成时间: 2025-09-12 23:13:11
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views/freemarker.FreemarkerViewRenderer;
import io.dropwizard.views/freemarker.FreemarkerViewLayout;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

public class RandomNumberGeneratorApp extends Application<RandomNumberGeneratorConfiguration> {

    // Define the RandomNumberResource class
    @Path("/random")
    public static class RandomNumberResource {
        private final Random random;

        public RandomNumberResource() {
            this.random = new Random();
        }

        // GET endpoint to generate a random number
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String getRandomNumber() {
            return String.valueOf(random.nextInt());
        }
    }

    public static void main(String[] args) throws Exception {
        new RandomNumberGeneratorApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<RandomNumberGeneratorConfiguration> bootstrap) {
        // Initialize the application
        bootstrap.addBundle(new ViewBundle<RandomNumberGeneratorConfiguration>() {
            @Override
            public void run(RandomNumberGeneratorConfiguration configuration, Environment environment) {
                super.run(configuration, environment);
                environment.getViewRenderers().register(new FreemarkerViewRenderer());
            }
        });
    }

    @Override
    public void run(RandomNumberGeneratorConfiguration configuration, Environment environment) throws Exception {
        // Run the application
        environment.jersey().register(new RandomNumberResource());
    }
}
