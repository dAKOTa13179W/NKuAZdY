// 代码生成时间: 2025-09-30 01:58:28
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

public class GameSaveSystem extends Application<GameSaveConfig> {

    private static final Logger LOGGER = Logger.getLogger(GameSaveSystem.class.getName());

    @Override
    public void initialize(Bootstrap<GameSaveConfig> bootstrap) {
        // Nothing to initialize in this simple example.
    }

    @Override
    public void run(GameSaveConfig configuration, Environment environment) throws Exception {
        // Registering a REST resource
        environment.jersey().register(new GameSaveResource());
    }

    /**
     * Main method for starting the application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) throws Exception {
        new GameSaveSystem().run(args);
    }
}

/**
 * GameSaveConfig.java
 *
 * Configuration class for the game save system.
 */
import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

public class GameSaveConfig extends Configuration {
    @NotNull
    private Path saveDirectory = Paths.get(".");

    public Path getSaveDirectory() {
        return saveDirectory;
    }

    public void setSaveDirectory(Path saveDirectory) {
        this.saveDirectory = saveDirectory;
    }
}

/**
 * GameSaveResource.java
 *
 * REST resource for handling game save operations.
 */
import io.dropwizard.jersey.DropwizardResource;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Path("/save")
public class GameSaveResource extends DropwizardResource {

    private final Path saveDirectory;

    public GameSaveResource(GameSaveConfig config) {
        this.saveDirectory = config.getSaveDirectory();
    }

    @POST
    @Path("/game/{gameId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveGame(LongParam gameId, String gameData) {
        try {
            Path filePath = saveDirectory.resolve(gameId.toString() + ".save");
            Files.write(filePath, gameData.getBytes(), StandardOpenOption.CREATE);
            return Response.ok("Game saved successfully.").build();
        } catch (IOException e) {
            LOGGER.severe("Error saving game data: " + e.getMessage());
            return Response.serverError().entity(new ErrorMessage(500, "Error saving game data.")).build();
        }
    }

    @GET
    @Path("/game/{gameId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response loadGame(LongParam gameId) {
        try {
            Path filePath = saveDirectory.resolve(gameId.toString() + ".save");
            if (Files.exists(filePath)) {
                String gameData = new String(Files.readAllBytes(filePath));
                return Response.ok(gameData).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Game data not found.").build();
            }
        } catch (IOException e) {
            LOGGER.severe("Error loading game data: " + e.getMessage());
            return Response.serverError().entity(new ErrorMessage(500, "Error loading game data.")).build();
        }
    }
}

/**
 * ErrorMessage.java
 *
 * Error message class for REST responses.
 */
import io.dropwizard.jersey.errors.ErrorMessage;

public class ErrorMessage extends ErrorMessage {
    public ErrorMessage(int status, String message) {
        super(status, message);
    }
}
