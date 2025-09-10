// 代码生成时间: 2025-09-11 04:03:10
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件夹结构整理器
 *
 * 功能：整理指定文件夹的文件和子文件夹，按文件类型进行归类。
 *
 * 使用方法：
 * FolderStructureOrganizer organizer = new FolderStructureOrganizer("/your/target/directory");
 * organizer.organize();
 */
public class FolderStructureOrganizer {
    private String targetDirectory;

    public FolderStructureOrganizer(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    /**
     * 整理文件夹结构
     *
     * @throws IOException 如果发生IO异常
     */
    public void organize() throws IOException {
        File dir = new File(targetDirectory);

        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Target directory does not exist or is not a directory.");
        }

        List<File> files = listFilesInDirectory(dir);
        files.forEach(file -> moveFileToCorrectDirectory(file));
    }

    /**
     * 列出目录中的所有文件
     *
     * @param dir 目录
     * @return 文件列表
     */
    private List<File> listFilesInDirectory(File dir) {
        List<File> files = new ArrayList<>();
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    /**
     * 根据文件类型将文件移动到相应的目录
     *
     * @param file 文件
     * @throws IOException 如果发生IO异常
     */
    private void moveFileToCorrectDirectory(File file) throws IOException {
        String fileName = file.getName();
        String fileExtension = getExtension(fileName);
        String targetDir = targetDirectory + "/" + fileExtension;

        File targetDirectory = new File(targetDir);
        if (!targetDirectory.exists()) {
            boolean created = targetDirectory.mkdir();
            if (!created) {
                throw new IOException("Failed to create directory for file type: " + fileExtension);
            }
        }

        FileUtils.moveFile(file, new File(targetDir, fileName));
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index > 0 ? fileName.substring(index + 1) : "unknown";
    }

    // 主函数用于测试
    public static void main(String[] args) {
        try {
            FolderStructureOrganizer organizer = new FolderStructureOrganizer("/your/target/directory");
            organizer.organize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
