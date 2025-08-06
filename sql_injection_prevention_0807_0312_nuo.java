// 代码生成时间: 2025-08-07 03:12:57
 * It shows how to create a simple REST API with a single endpoint that interacts
 * with a database while avoiding SQL injection vulnerabilities.
 */

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Path("/users")
public class SqlInjectionPreventionResource {
    private final SessionFactory sessionFactory;

    public SqlInjectionPreventionResource(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @GET
    public Response getUserById(@QueryParam("id") String id) {
        try (Session session = sessionFactory.openSession()) {
            // Using Hibernate Criteria API to prevent SQL injection
            Long userId = Long.parseLong(id); // Ensure that the query parameter is a valid long
            User user = session.get(User.class, userId);
            if (user != null) {
                return Response.ok(user).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
        } catch (NumberFormatException e) {
            // Handle the case where the id is not a valid long
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID format").build();
        }
    }
}

public class SqlInjectionPreventionApplication extends Application<SqlInjectionPreventionConfiguration> {
    public static void main(String[] args) throws Exception {
        new SqlInjectionPreventionApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SqlInjectionPreventionConfiguration> bootstrap) {
        // Initialize any additional Dropwizard bundles
    }

    @Override
    public void run(SqlInjectionPreventionConfiguration configuration, Environment environment) throws Exception {
        final HibernateBundle<SqlInjectionPreventionConfiguration> hibernate = new HibernateBundle<SqlInjectionPreventionConfiguration>(User.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(SqlInjectionPreventionConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
        final SessionFactory sessionFactory = hibernate.getSessionFactory();
        environment.jersey().register(new SqlInjectionPreventionResource(sessionFactory));
    }
}

// User entity class
public class User {
    private Long id;
    private String name;
    // Getters and setters omitted for brevity
}
