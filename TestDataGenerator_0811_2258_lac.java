// 代码生成时间: 2025-08-11 22:58:11
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
# FIXME: 处理边界情况
import io.dropwizard.views.ViewBundle;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/test")
public class TestDataResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generateTestData() {
        // Generate random test data
# 优化算法效率
        Random rand = new Random();
        int id = rand.nextInt(1000);
        String name = "Test User " + id;
        int age = rand.nextInt(100);

        // Return JSON representation of the test data
        return "{"id": " + id + ", "name": "" + name + "", "age": " + age + "}";
    }
}

public class TestDataGenerator extends Application<TestDataGeneratorConfiguration> {

    public static void main(String[] args) throws Exception {
        new TestDataGenerator().run(args);
    }

    @Override
    public void initialize(Bootstrap<TestDataGeneratorConfiguration> bootstrap) {
        // Initialize the application
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(TestDataGeneratorConfiguration configuration, Environment environment) {
        // Register resources
        environment.jersey().register(new TestDataResource());
    }
}
