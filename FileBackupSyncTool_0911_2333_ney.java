// 代码生成时间: 2025-09-11 23:33:50
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileBackupSyncTool {

    // Method to backup files from source to destination directory
    public void backupFiles(String sourceDir, String backupDir) {
        try {
            Files.walk(Paths.get(sourceDir)).forEach(sourcePath -> {
                String relativePath = sourcePath.toString().substring(sourceDir.length() + 1);
                String targetPath = backupDir + relativePath;
                File targetFile = new File(targetPath);

                // Create parent directories if they do not exist
                targetFile.getParentFile().mkdirs();

                // Copy file from source to backup directory
                Files.copy(sourcePath, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            });
        } catch (IOException e) {
            System.err.println("Error occurred during backup: " + e.getMessage());
        }
    }

    // Method to synchronize files between source and target directories
    public void syncFiles(String sourceDir, String targetDir) {
        try {
            // Get all files in source directory
            File sourceDirFile = new File(sourceDir);
            if (!sourceDirFile.exists() || !sourceDirFile.isDirectory()) {
                throw new IllegalArgumentException("Source directory does not exist or is not a directory.");
            }

            // Get all files in target directory
            File targetDirFile = new File(targetDir);
            if (!targetDirFile.exists() || !targetDirFile.isDirectory()) {
                throw new IllegalArgumentException("Target directory does not exist or is not a directory.");
            }

            // Compare files in source and target directories and update target directory
            Objects.requireNonNull(sourceDirFile.list()).forEach(file -> {
                File sourceFile = new File(sourceDir, file);
                File targetFile = new File(targetDir, file);

                if (sourceFile.isFile() && !targetFile.exists()) {
                    // If file exists in source but not in target, copy to target
                    FileUtils.copyFile(sourceFile, targetFile);
                } else if (sourceFile.isFile() && targetFile.exists()) {
                    // If file exists in both, compare and update if necessary
                    if (!Files.isSameFile(sourceFile.toPath(), targetFile.toPath())) {
                        FileUtils.copyFile(sourceFile, targetFile);
                    }
                }
            });
            Objects.requireNonNull(targetDirFile.list()).forEach(file -> {
                File targetFile = new File(targetDir, file);
                File sourceFile = new File(sourceDir, file);

                if (targetFile.isFile() && !sourceFile.exists()) {
                    // If file exists in target but not in source, delete from target
                    targetFile.delete();
                }
            });
        } catch (IOException e) {
            System.err.println("Error occurred during synchronization: " + e.getMessage());
        }
    }

    // Main method to test the backup and sync functionality
    public static void main(String[] args) {
        FileBackupSyncTool tool = new FileBackupSyncTool();
        String sourceDir = "/path/to/source";
        String backupDir = "/path/to/backup";
        String targetDir = "/path/to/target";

        try {
            System.out.println("Starting backup...");
            tool.backupFiles(sourceDir, backupDir);
            System.out.println("Backup completed successfully.");

            System.out.println("Starting synchronization...");
            tool.syncFiles(sourceDir, targetDir);
            System.out.println("Synchronization completed successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}