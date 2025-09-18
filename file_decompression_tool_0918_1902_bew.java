// 代码生成时间: 2025-09-18 19:02:52
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.compress.utils.ArchiveUtils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A utility class to decompress files using the Dropwizard framework and Apache Commons Compress.
 */
public class FileDecompressionTool {

    private static final String ARCHIVE_FORMAT = "zip"; // Currently only supports ZIP files

    /**
     * Decompresses a file from the source path to the target directory.
     * 
     * @param sourcePath The path to the file to decompress.
     * @param targetDirectory The directory where the decompressed files will be placed.
     * @throws IOException If an I/O error occurs.
     * @throws ArchiveException If an archive error occurs.
     */
    public void decompressFile(String sourcePath, String targetDirectory) throws IOException, ArchiveException {
        // Ensure the target directory exists
        Files.createDirectories(Paths.get(targetDirectory));

        // Create a new file input stream for the source file
        try (InputStream inputStream = new FileInputStream(sourcePath);
             ArchiveStreamFactory archiveStreamFactory = new ArchiveStreamFactory()) {

            // Create an archive input stream from the factory
            ArchiveEntry entry;
            try (InputStream archiveInputStream = archiveStreamFactory.createArchiveInputStream(ARCHIVE_FORMAT, inputStream)) {
                // Read entries from the archive
                while ((entry = (ArchiveEntry) archiveInputStream.getNextEntry()) != null) {
                    // Construct the path for the extracted file
                    String fileName = entry.getName();
                    Path outputPath = Paths.get(targetDirectory, fileName);

                    // Check if the current entry is a directory or a file
                    if (entry.isDirectory()) {
                        // Create directory if it doesn't exist
                        Files.createDirectories(outputPath);
                    } else {
                        // Create parent directories if they don't exist
                        Files.createDirectories(outputPath.getParent());

                        // Copy file content from the archive to the output file
                        try (OutputStream outputStream = Files.newOutputStream(outputPath)) {
                            IOUtils.copy(archiveInputStream, outputStream);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Example usage of the decompression tool
        FileDecompressionTool tool = new FileDecompressionTool();
        try {
            // Replace with your own file path and target directory
            String sourcePath = "path/to/your/file.zip";
            String targetDirectory = "path/to/your/target/directory";
            tool.decompressFile(sourcePath, targetDirectory);
            System.out.println("Decompression completed successfully.");
        } catch (IOException | ArchiveException e) {
            e.printStackTrace();
            System.out.println("An error occurred during decompression: " + e.getMessage());
        }
    }
}
