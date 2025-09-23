// 代码生成时间: 2025-09-23 11:16:06
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;

public class MemoryUsageAnalysis {

    private static final String OPTION_HELP = "h";
    private static final String OPTION_SHOW_USAGE = "s";
    private static final String OPTION_HELP_LONG = "help";
    private static final String OPTION_SHOW_USAGE_LONG = "showUsage";

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder(OPTION_HELP).longOpt(OPTION_HELP_LONG).hasArg(false).desc("Displays help").build());
        options.addOption(Option.builder(OPTION_SHOW_USAGE).longOpt(OPTION_SHOW_USAGE_LONG).hasArg(false).desc("Shows memory usage").build());

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(OPTION_HELP)) {
                printHelp(options);
                return;
            }
            if (cmd.hasOption(OPTION_SHOW_USAGE)) {
                showMemoryUsage();
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("memoryUsageAnalysis", options);
    }

    private static void showMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory Usage: " + heapMemoryUsage);
        System.out.println("Non-Heap Memory Usage: " + nonHeapMemoryUsage);
    }
}
