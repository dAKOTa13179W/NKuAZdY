// 代码生成时间: 2025-08-25 08:20:55
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/inventory")
public class InventoryResource {
    private final Map<String, Integer> inventory;

    public InventoryResource(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

public class InventoryManagementApp extends Application<InventoryConfig> {
    public static void main(String[] args) throws Exception {
        new InventoryManagementApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<InventoryConfig> bootstrap) {
        // Initialize any additional configuration or resources
        bootstrap.addBundle(new ViewBundle<>());
    }

    @Override
    public void run(InventoryConfig configuration, Environment environment) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Integer> inventory = configuration.getInventory();
        try {
            environment.jersey().register(new InventoryResource(inventory));
        } catch (Exception e) {
            throw new RuntimeException("Failed to register InventoryResource", e);
        }
    }
}

class InventoryConfig extends Configuration {
    private Map<String, Integer> inventory;

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }
}
