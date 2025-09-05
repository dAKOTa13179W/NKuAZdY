// 代码生成时间: 2025-09-05 18:11:36
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileDecompressor extends Application<FileDecompressorConfiguration> {

    public static void main(String[] args) throws Exception {
        new FileDecompressor().run(args);
    }

    @Override
    public void initialize(Bootstrap<FileDecompressorConfiguration> bootstrap) {
        // Initialization code here
    }

    @Override
    public void run(FileDecompressorConfiguration configuration, Environment environment) {
        ArgumentParser parser = ArgumentParsers.newFor("FileDecompressor").build();
        parser.addArgument("-i", "--input").required(true).help("Input file path");
        parser.addArgument("-o", "--output").required(true).help("Output directory path");
        Namespace namespace = parser.parseArgs(args);

        try {
            File inputFile = new File(namespace.getString("input"));
            File outputDir = new File(namespace.getString("output"));
            decompressFile(inputFile, outputDir);
        } catch (IOException | ArchiveException e) {
            environment.metrics().counter("decompression.errors").inc();
            throw new RuntimeException("Error decompressing file", e);
        }
    }

    private void decompressFile(File inputFile, File outputDir) throws IOException, ArchiveException {
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IllegalArgumentException("Input file does not exist or is not a file");
        }

        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IllegalArgumentException("Output directory could not be created");
        }

        try (InputStream inputStream = new FileInputStream(inputFile);
             ArchiveInputStream archiveInputStream = (ArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("zip", inputStream)) {

            ArchiveEntry entry = archiveInputStream.getNextEntry();
            while (entry != null) {
                if (!entry.isDirectory()) {
                    extractFile(archiveInputStream, outputDir, entry);
                }
                entry = archiveInputStream.getNextEntry();
            }
        }
    }

    private void extractFile(ArchiveInputStream archiveInputStream, File outputDir, ArchiveEntry entry) throws IOException {
        File outputFile = new File(outputDir, entry.getName());

        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            IOUtils.copy(archiveInputStream, outputStream);
        }
    }
}

class FileDecompressorConfiguration extends Configuration {
    // Configuration class
}
