// 代码生成时间: 2025-08-04 16:59:39
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import java.net.URL;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/validate")
public class UrlValidatorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateURL(@QueryParam("url") String urlString) {
        try {
            URL url = new URL(urlString);
            return Response.ok().entity({"isValid": true, "message": "URL is valid."}).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity({"isValid": false, "message": "Invalid URL format."}).build();
        }
    }
}

public class UrlValidatorApplication extends Application<UrlValidatorConfiguration> {

    public static void main(String[] args) throws Exception {
        new UrlValidatorApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<UrlValidatorConfiguration> bootstrap) {
        // Add any initialization code here if needed.
    }

    @Override
    public void run(UrlValidatorConfiguration configuration, Environment environment) {
        environment.jersey().register(new UrlValidatorResource());
        environment.jersey().register(new ViewBundle());
    }
}

// This is a placeholder configuration class that you should extend with your own properties.
class UrlValidatorConfiguration extends Configuration {
    // Add your own properties here.
}
