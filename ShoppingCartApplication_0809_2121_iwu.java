// 代码生成时间: 2025-08-09 21:21:54
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer.Builder;
import io.dropwizard.views.mustache.MustacheTemplateRenderer;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/cart")
public class ShoppingCartResource {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartResource.class);
    private final Map<String, Integer> cart;
    private final Map<String, Integer> order;

    public ShoppingCartResource() {
        this.cart = new HashMap<>();
        this.order = new HashMap<>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getCart() {
        logger.info("Retrieving cart items");
        return cart;
    }

    @POST
    @Path("/add/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> addToCart(@PathParam("itemId") String itemId) {
        if (cart.containsKey(itemId)) {
            cart.put(itemId, cart.get(itemId) + 1);
        } else {
            cart.put(itemId, 1);
        }
        logger.info("Item added to cart: {}", itemId);
        return cart;
    }

    @POST
    @Path("/remove/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> removeFromCart(@PathParam("itemId") String itemId) {
        if (cart.containsKey(itemId)) {
            if (cart.get(itemId) > 1) {
                cart.put(itemId, cart.get(itemId) - 1);
            } else {
                cart.remove(itemId);
            }
            logger.info("Item removed from cart: {}", itemId);
        } else {
            logger.warn("Item not found in cart: {}", itemId);
        }
        return cart;
    }

    @POST
    @Path("/order")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> placeOrder() {
        try {
            order.putAll(cart);
            cart.clear();
            logger.info("Order placed successfully");
            return order;
        } catch (Exception e) {
            logger.error("Failed to place order", e);
            return new HashMap<>();
        }
    }
}

public class ShoppingCartApplication extends Application<ShoppingCartConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartApplication.class);

    @Override
    public void initialize(Bootstrap<ShoppingCartConfiguration> bootstrap) {
        ResourceConfig config = new ResourceConfig();
        config.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        bootstrap.addBundle(new ConfigurationResourceProvider(config));
    }

    @Override
    public void run(ShoppingCartConfiguration configuration, Environment environment) throws Exception {
        Builder<MustacheTemplateRenderer> builder = new Builder<>();
        builder.withLoader(
                new ClassPathViewLoader(configuration.getTemplatePath())
        ).withRenderer(new MustacheViewRenderer());
        environment.views.register(MustacheTemplateRenderer.class);
        environment.jersey().register(new ShoppingCartResource());
    }
}

import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShoppingCartConfiguration extends Configuration {
    @Valid
    @NotNull
    private String templatePath;

    @JsonProperty
    public String getTemplatePath() {
        return templatePath;
    }

    @JsonProperty
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
