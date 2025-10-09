// 代码生成时间: 2025-10-09 18:32:02
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Path("/csrf")
public class CsrfProtectionResource {

    private final String csrfToken;

    public CsrfProtectionResource(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    @GET
    @Path("generate-token")
    public Response generateCsrfToken() {
        // CSRF token is generated and sent to the client.
        return Response
                .ok(csrfToken)
                .cookie(new NewCookie("CSRF-TOKEN", csrfToken, "/", null, false, 30, TimeUnit.DAYS, false))
                .build();
    }

    @POST
    @Path("submit-form")
    public Response submitForm(
            @QueryParam("csrf-token") String clientToken,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the CSRF token sent to the client from the session.
        String sessionToken = request.getCookies()[1].getValue();
        
        // Check if the CSRF tokens match.
        if (csrfToken.equals(clientToken) && csrfToken.equals(sessionToken)) {
            // CSRF tokens match, process the form submission.
            return Response.ok("Form submission successful!").build();
        } else {
            // CSRF tokens do not match, throw an error.
            return Response.status(Response.Status.FORBIDDEN).entity("CSRF token mismatch.").build();
        }
    }
}

public class CsrfProtectionApp extends Application<CsrfProtectionAppConfiguration> {

    @Override
    public void initialize(Bootstrap<CsrfProtectionAppConfiguration> bootstrap) {
        // Initialize the ViewBundle to render HTML views.
        bootstrap.addBundle(new ViewBundle<CsrfProtectionAppConfiguration>());
    }

    @Override
    public void run(CsrfProtectionAppConfiguration configuration, Environment environment) throws Exception {
        // Generate a CSRF token.
        String csrfToken = UUID.randomUUID().toString();
        
        // Register the resource with the Dropwizard environment.
        environment.jersey().register(new CsrfProtectionResource(csrfToken));
    }
}

// The configuration class is not shown here for brevity.
// It should extend io.dropwizard.Configuration and include any necessary configuration properties.
