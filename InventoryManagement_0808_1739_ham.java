// 代码生成时间: 2025-08-08 17:39:21
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/inventory")
public class InventoryResource {
    // In-memory storage for inventory items
    private final Map<String, Integer> inventory = new HashMap<>();

    public InventoryResource() {
        // Initialize inventory with some items
        inventory.put("item1", 100);
        inventory.put("item2", 200);
    }

    // GET method to retrieve all items in inventory
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getAllItems() {
        return inventory;
    }

    // POST method to add new item to inventory
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> addItem(Item item) {
        if (inventory.containsKey(item.getId())) {
            throw new WebApplicationException("Item already exists in inventory", 409);
        }
        inventory.put(item.getId(), item.getQuantity());
        return inventory;
    }

    // PUT method to update quantity of an existing item
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> updateItem(@PathParam("id") String id, Item item) {
        if (!inventory.containsKey(id)) {
            throw new WebApplicationException("Item does not exist in inventory", 404);
        }
        inventory.put(id, item.getQuantity());
        return inventory;
    }

    // DELETE method to remove an item from inventory
    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> removeItem(@PathParam("id\) String id) {
        if (!inventory.containsKey(id)) {
            throw new WebApplicationException("Item does not exist in inventory", 404);
        }
        inventory.remove(id);
        return inventory;
    }
}

// Simple POJO class to represent an item in inventory
class Item {
    private String id;
    private int quantity;

    public Item(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}

// Main application class
public class InventoryManagement extends Application<InventoryManagementConfiguration> {

    @Override
    public void initialize(Bootstrap<InventoryManagementConfiguration> bootstrap) {
        // Initialize any additional configurations or resources here
    }

    @Override
    public void run(InventoryManagementConfiguration configuration, Environment environment) {
        // Register REST resources
        environment.jersey().register(new InventoryResource());

        // Register ViewBundle for templating
        environment.views().register(InventoryView.class);
        environment.jersey().register(new ViewMessageBodyWriter<>(new InventoryViewRenderer()));
    }

    public static void main(String[] args) throws Exception {
        new InventoryManagement().run(args);
    }
}
