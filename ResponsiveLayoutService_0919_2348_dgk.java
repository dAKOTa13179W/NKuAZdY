// 代码生成时间: 2025-09-19 23:48:01
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class ResponsiveLayoutService extends Application<ResponsiveLayoutConfiguration> {

    // Define a public main method to run the application
    public static void main(String[] args) throws Exception {
        new ResponsiveLayoutService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ResponsiveLayoutConfiguration> bootstrap) {
        // Register the ViewsBundle for HTML templating support
        bootstrap.addBundle(new ViewBundle<ResponsiveLayoutConfiguration>());
        // Register the AssetsBundle for serving static files like CSS, JS, etc.
        bootstrap.addBundle(new AssetsBundle("/", "/", "index.html"));
    }

    @Override
    public void run(ResponsiveLayoutConfiguration configuration, Environment environment) {
        // Define a route to render the responsive layout view
        environment.jersey().register(new ResponsiveLayoutResource());
    }
}
