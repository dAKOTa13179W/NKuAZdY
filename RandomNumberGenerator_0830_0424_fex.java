// 代码生成时间: 2025-08-30 04:24:45
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomNumberGenerator extends Application<RandomNumberGeneratorConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(RandomNumberGenerator.class);

    @Override
    public void initialize(Bootstrap<RandomNumberGeneratorConfiguration> bootstrap) {
        // Nothing to initialize for this example
    }

    @Override
    public void run(RandomNumberGeneratorConfiguration configuration, Environment environment) throws Exception {
        // Register a new route for the random number generation
        environment.jersey().register(new RandomNumberResource());
    }

    public static void main(String[] args) throws Exception {
        new RandomNumberGenerator().run(args);
    }
}

/**
 * RandomNumberResource.java
 * 
 * This class represents a REST resource for generating random numbers.
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/random")
public class RandomNumberResource {
    private static final Random random = new Random();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String generateRandomNumber() {
        int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
        return String.valueOf(randomNumber);
    }
}

/**
 * RandomNumberGeneratorConfiguration.java
 * 
 * This class represents the configuration for the application.
 */
import io.dropwizard.Configuration;

public class RandomNumberGeneratorConfiguration extends Configuration {
    // No configuration parameters needed for this example
}
