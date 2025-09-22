// 代码生成时间: 2025-09-22 15:26:53
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// FolderOrganizerApplication is the main class for the Dropwizard application
public class FolderOrganizerApplication extends Application<FolderOrganizerConfiguration> {

    // Constructor for the Application
    public FolderOrganizerApplication() {
    }

    // Define the main method to run the application
    public static void main(String[] args) throws Exception {
        new FolderOrganizerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<FolderOrganizerConfiguration> bootstrap) {
        // Define configuration class
        bootstrap.addCommand(new FolderOrganizerCommand());
    }

    @Override
    public void run(FolderOrganizerConfiguration configuration, Environment environment) throws Exception {
        // Create a new FolderOrganizerService instance
        FolderOrganizerService service = new FolderOrganizerService(configuration.getFolderPath());

        // Register the service with the environment
        environment.jersey().register(service);
    }
}

// FolderOrganizerConfiguration is the configuration class for the application
class FolderOrganizerConfiguration extends Configuration {
    // Path for the folder to organize
    private String folderPath;

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}

// FolderOrganizerCommand is the command line argument parser for the application
class FolderOrganizerCommand extends Command {
    public FolderOrganizerCommand() {
        super("organize", "Organize the specified folder structure");
    }

    @Override
    public void configureSubparser(Subparser subparser) {
        // Define the command line arguments
        subparser.addArgument("-f", "--folder")
                .type(String.class)
                .required(true)
                .dest("folder\)
                .help("The path of the folder to organize");
    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {
        // Set the folder path in the configuration
        FolderOrganizerConfiguration configuration = new FolderOrganizerConfiguration();
        configuration.setFolderPath(namespace.getString("folder"));

        // Run the application with the configured path
        bootstrap.run(configuration);
    }
}

// FolderOrganizerService is the service class responsible for organizing the folder structure
@Path("/organize")
public class FolderOrganizerService {
    private final String folderPath;

    // Constructor for the service
    public FolderOrganizerService(String folderPath) {
        this.folderPath = folderPath;
    }

    // Organize the folder structure
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String organizeFolder() {
        try {
            // Make sure the folder exists
            Path path = Paths.get(folderPath);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("The specified folder does not exist");
            }

            // Organize the folder structure
            organizeFolderStructure(path);

            // Return a success message
            return "Folder organized successfully";
        } catch (IOException e) {
            // Handle I/O errors
            return "Error organizing folder: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            // Handle invalid folder path
            return "Error: " + e.getMessage();
        }
    }

    // Recursively organize the folder structure
    private void organizeFolderStructure(Path path) throws IOException {
        // If the path is a file, move it to the appropriate subfolder
        if (Files.isRegularFile(path)) {
            // Determine the appropriate subfolder
            String filename = path.getFileName().toString();
            String extension = filename.substring(filename.lastIndexOf('.') + 1);
            Path subfolder = path.resolveSibling("." + extension);

            // Create the subfolder if it doesn't exist
            if (!Files.exists(subfolder)) {
                Files.createDirectory(subfolder);
            }

            // Move the file to the subfolder
            Files.move(path, subfolder.resolve(filename));
        } else if (Files.isDirectory(path)) {
            // If the path is a directory, recursively organize its contents
            Files.list(path).forEach(child -> {
                try {
                    organizeFolderStructure(child);
                } catch (IOException e) {
                    // Handle I/O errors
                    e.printStackTrace();
                }
            });
        }
    }
}