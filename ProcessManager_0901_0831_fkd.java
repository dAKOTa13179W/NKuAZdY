// 代码生成时间: 2025-09-01 08:31:47
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProcessManager is a utility class to manage system processes.
 * It provides methods to list, start, and kill processes.
 */
public class ProcessManager {

    private static final String OS_TYPE = System.getProperty("os.name").toLowerCase();
    private static final String KILL_COMMAND = SystemUtils.IS_OS_WINDOWS ? "taskkill /F /IM " : "kill -9 ";

    private ProcessManager() {
        // Private constructor to prevent instantiation of this utility class.
    }

    /**<ol>
     * Lists all running processes on the system.
     *
     * @return A list of process names.
     * @throws IOException If an I/O error occurs.
     */
    public static List<String> listProcesses() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> processNames = new ArrayList<>();

        if (SystemUtils.IS_OS_WINDOWS) {
            processBuilder.command("tasklist");
        } else {
            processBuilder.command("ps", "aux");
        }

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    processNames.add(StringUtils.trimToEmpty(line.split(" ")[0]));
                }
            }
        }
        return processNames;
    }

    /**<ol>
     * Starts a new process with the given command.
     *
     * @param command The command to execute.
     * @throws IOException If an I/O error occurs.
     */
    public static void startProcess(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.start();
    }

    /**<ol>
     * Kills a process with the given name.
     *
     * @param processName The name of the process to kill.
     * @throws IOException If an I/O error occurs.
     */
    public static void killProcess(String processName) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                SystemUtils.IS_OS_WINDOWS ? "taskkill" : "kill",
                "/F", "/IM", processName);
        processBuilder.start();
    }

    /**<ol>
     * Main method to demonstrate the usage of ProcessManager.
     *
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        // List all processes
        List<String> processes = listProcesses();
        System.out.println("Running processes: " + processes);

        // Start a new process
        startProcess("notepad"); // Example: Start Notepad on Windows

        // Kill a process
        killProcess("notepad.exe"); // Example: Kill Notepad on Windows
    }
}
