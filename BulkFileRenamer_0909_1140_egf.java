// 代码生成时间: 2025-09-09 11:40:47
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Namespace;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class BulkFileRenamer extends Application<Configuration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkFileRenamer.class);

    // Entry point for the application
    public static void main(String[] args) throws Exception {
        new BulkFileRenamer().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        // Initialize any resources here
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        // Set up the argument parser
        ArgumentParser parser = ArgumentParsers.newFor("BulkFileRenamer").build();
        Subparsers subparsers = parser.addSubparsers().dest("command");

        // Define a subparser for the 'rename' command
        var renameParser = subparsers.addParser("rename")
                .help("Rename files in a directory with a specified prefix");

        renameParser.addArgument("-d", "--directory")
                .required(true)
                .type(String.class)
                .dest("directory")
                .help("Directory containing files to rename");

        renameParser.addArgument("-p", "--prefix")
                .required(true)
                .type(String.class)
                .dest("prefix\)
                .help("Prefix to add to each file name");

        // Parse the arguments
        Namespace namespace = parser.parseArgs(args);

        // Perform the file renaming based on the parsed arguments
        if (namespace.getString("command\).equals("rename")) {
            String directory = namespace.getString("directory");
            String prefix = namespace.getString("prefix");
            renameFilesInDirectory(directory, prefix);
        }
    }

    /**
     * Renames all files in the specified directory with the given prefix.
     *
     * @param directory The directory containing files to rename.
     * @param prefix    The prefix to add to each file name.
     */
    private void renameFilesInDirectory(String directory, String prefix) {
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            LOGGER.error("The specified path is not a directory: {}", directory);
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            LOGGER.error("Failed to list files in directory: {}", directory);
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String newFileName = prefix + fileName;
                File newFile = new File(dir, newFileName);

                // Rename the file
                if (file.renameTo(newFile)) {
                    LOGGER.info("Renamed file from {} to {}", fileName, newFileName);
                } else {
                    LOGGER.error("Failed to rename file from {} to {}", fileName, newFileName);
                }
            }
        }
    }
}
