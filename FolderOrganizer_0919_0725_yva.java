// 代码生成时间: 2025-09-19 07:25:24
import com.dropwizard.Application;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.dropwizard.views.View;
import com.dropwizard.views.ViewBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Path("/organize")
public class FolderOrganizerResource {

    private final String rootDirectory;

    public FolderOrganizerResource(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> organizeFiles() throws IOException {
        try {
            Path rootPath = Paths.get(rootDirectory);
            Files.walk(rootPath)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        String filename = file.getFileName().toString();
                        String directoryPath = rootPath.resolve(filename.substring(0, 1)).toString();
                        Files.createDirectories(Paths.get(directoryPath));
                        Files.move(file, Paths.get(directoryPath + File.separator + filename));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            return Files.walk(rootPath)
                .filter(Files::isDirectory)
                .map(Object::toString)
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class FolderOrganizerApplication extends Application<FolderOrganizerConfiguration> {

    public static void main(String[] args) throws Exception {
        new FolderOrganizerApplication().run(args);
    }

    @Override
    public String getName() {
        return "FolderOrganizer";
    }

    @Override
    public void initialize(Bootstrap<FolderOrganizerConfiguration> bootstrap) {
        // Nothing to do here
    }

    @Override
    public void run(FolderOrganizerConfiguration configuration, Environment environment) {
        environment.jersey().register(new FolderOrganizerResource(configuration.getRootDirectory()));
        environment.views().register(FreemarkerViewRenderer.class);
        environment.views().register(MustacheViewRenderer.class);
        new AssetsBundle("/assets/", "/assets").run(environment);
        environment.jersey().setUrlPattern("/api/*");
    }
}

/**
 * Configuration class for FolderOrganizer application.
 */
public class FolderOrganizerConfiguration extends Configuration {

    private String rootDirectory;

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
}
