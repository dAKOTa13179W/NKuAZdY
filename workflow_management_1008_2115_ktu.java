// 代码生成时间: 2025-10-08 21:15:49
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.EnumSet;
import java.util.Set;

/**
 * Main application class for the Dropwizard workflow management application.
 */
@SwaggerDefinition(
    info = @Info(
        title = "Workflow Management API",
        version = "1.0.0",
        description = "API for managing workflow processes",
        contact = @Contact(name = "Your Name", email = "your.email@example.com", url = "http://www.example.com"),
        license = @License(name = "Your License", url = "http://www.example.com/license")
    )
)
@ApplicationPath("/api")
public class WorkflowManagementApplication extends Application<WorkflowManagementConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowManagementApplication.class);

    @Override
    public void initialize(Bootstrap<WorkflowManagementConfiguration> bootstrap) {
        // Initialize any additional components here
        bootstrap.addBundle(new ViewBundle<WorkflowManagementConfiguration>()
                .addRenderer(new FreemarkerViewRenderer())
                .addRenderer(new MustacheViewRenderer()));
    }

    @Override
    public void run(WorkflowManagementConfiguration configuration, Environment environment) {
        // Register resources and providers
        final WorkflowManagementResource workflowResource = new WorkflowManagementResource();
        environment.jersey().register(workflowResource);
        environment.jersey().register(MultiPartFeature.class);

        // Configure Swagger
        configureSwagger(environment);
    }

    /**
     * Configures Swagger for the application.
     * 
     * @param environment The environment to configure Swagger for.
     */
    private void configureSwagger(Environment environment) {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.example.workflowmanagement");
        beanConfig.setScan(true);
        environment.jersey().register(new SwaggerFeatureWrapper(beanConfig));
    }
}
