// 代码生成时间: 2025-08-06 23:15:29
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/api/formatter")
public class ApiResponseFormatterResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response formatApiResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "API Response格式化工具");
        response.put("status", "success");
        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response formatApiResponse(Map<String, Object> input) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "API Response formatted");
        response.put("status", "success");
        response.put("input", input.toString());
        return Response.ok(response).build();
    }
}

public class ApiResponseFormatterApplication extends Application<ApiResponseFormatterConfiguration> {

    @Override
    public void initialize(Bootstrap<ApiResponseFormatterConfiguration> bootstrap) {
        // Initialize any additional Dropwizard configuration here if necessary
        bootstrap.addBundle(new ViewBundle<ApiResponseFormatterConfiguration>()
                .addRenderer(new FreemarkerViewRenderer())
                .addRenderer(new MustacheViewRenderer()));
    }

    @Override
    public void run(ApiResponseFormatterConfiguration configuration, Environment environment) {
        environment.jersey().register(new ApiResponseFormatterResource());
    }
}

// ApiResponseFormatterConfiguration class is assumed to be defined in your project
// It should extend io.dropwizard.Configuration
// and contain any configuration parameters required by your application
/*
public class ApiResponseFormatterConfiguration extends Configuration {
    // Define configuration parameters here
}
*/
