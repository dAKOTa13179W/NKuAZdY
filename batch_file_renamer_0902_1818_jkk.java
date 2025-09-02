// 代码生成时间: 2025-09-02 18:18:28
import io.dropwizard.Application;
import io.dropwizard.configuration.Environment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Configuration;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Namespace;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class BatchFileRenamer extends Application<BatchFileRenamerConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchFileRenamer.class);

    public static void main(String[] args) throws Exception {
        new BatchFileRenamer().run(args);
    }

    @Override
    public void initialize(Bootstrap<BatchFileRenamerConfiguration> bootstrap) {
        // Initialize any additional components here
    }

    @Override
    public void run(BatchFileRenamerConfiguration configuration, Environment environment) {
        // Parse command line arguments
        Namespace namespace = parseArguments();

        // Perform file renaming
        try {
            performBatchRename(namespace.getString("directory"), namespace.getString("new_extension"));
        } catch (IOException e) {
            LOGGER.error("Error during file renaming", e);
        }
    }

    private Namespace parseArguments() {
        ArgumentParser parser = ArgumentParsers.newFor("batch-file-renamer").build();
        parser.addArgument("-d", "--directory").required(true).help("Directory to rename files in");
        parser.addArgument("-n", "--new_extension").required(true).help("New file extension to rename to");

        return parser.parseArgs();
    }

    private void performBatchRename(String directoryPath, String newExtension) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("The provided directory path is not a directory");
        }

        List<File> files = (List<File>) FileUtils.listFiles(directory, new SuffixFileFilter(".*"), TrueFileFilter.INSTANCE);

        for (File file : files) {
            Path oldPath = Paths.get(file.getAbsolutePath());
            Path newPath = Paths.get(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.')) + '.' + newExtension);

            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Renamed {} to {}", oldPath.getFileName(), newPath.getFileName());
        }
    }
}

// Configuration class for Dropwizard
class BatchFileRenamerConfiguration extends Configuration {
    // Add any configuration properties here
}
