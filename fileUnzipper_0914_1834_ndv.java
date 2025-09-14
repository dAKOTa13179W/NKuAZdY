// 代码生成时间: 2025-09-14 18:34:12
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

/**
 * A utility class to unzip files using Apache Commons Compress library.
 */
public class FileUnzipper {

    /**
     * Unzips a zip file to a specified directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The destination directory where files will be unzipped.
     * @throws IOException If an I/O error occurs during the unzipping process.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipArchiveInputStream zipIn = new ZipArchiveInputStream(new FileInputStream(zipFilePath));
        ZipArchiveEntry entry = zipIn.getNextZipEntry();
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // If the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // If the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextZipEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a file from the zip input stream.
     *
     * @param zis The zip input stream.
     * @param filePath The path to the file to be extracted.
     * @throws IOException If an I/O error occurs during extraction.
     */
    private void extractFile(ZipArchiveInputStream zis, String filePath) throws IOException {
       缓冲区大小
        byte[] bytes = new byte[4096];
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());

        try (FileOutputStream fos = new FileOutputStream(filePath);
             InputStream is = zis) {
            while (true) {
                int read = is.read(bytes);
                if (read == -1) {
                    break;
                }
                fos.write(bytes, 0, read);
            }
        }
    }

    /**
     * Main method to test the unzip functionality.
     *
     * @param args The command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        FileUnzipper unzipper = new FileUnzipper();
        String zipFilePath = "path/to/your.zip"; // Replace with your zip file path
        String destDirectory = "path/to/destination"; // Replace with your destination directory
        unzipper.unzip(zipFilePath, destDirectory);
        System.out.println("Unzipping completed.");
    }
}
