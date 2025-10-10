// 代码生成时间: 2025-10-10 19:25:46
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * CPU使用率分析器
 *
 * 提供方法获取系统CPU使用率
 */
public class CpuUsageAnalyzer {

    private static final long REFRESH_INTERVAL_MS = 1000; // 刷新间隔1秒
    private double previousCpuLoad = 0.0; // 上一次CPU负载
    private long previousTimestamp = 0; // 上一次时间戳

    /**
     * 获取当前CPU使用率
     *
     * @return 当前CPU使用率
     */
    public double getCpuUsage() {
        long[] prevTicks = getTicks();
        long now = System.nanoTime();

        TimeUnit.MILLISECONDS.sleep(REFRESH_INTERVAL_MS); // 等待刷新间隔

        long[] ticks = getTicks();
        long deltaCpu = ticks[0] - prevTicks[0];
        long deltaTime = now - previousTimestamp;

        if (deltaTime > 0) {
            double cpuUsage = (deltaCpu / (double) (deltaTime / (double) TimeUnit.NANOSECONDS.toMillis(1))) * 100.0;
            previousCpuLoad = Math.max(previousCpuLoad, cpuUsage);
            return previousCpuLoad;
        } else {
            return 0.0;
        }
    }

    /**
     * 获取CPU时间片信息
     *
     * @return CPU时间片信息数组
     */
    private long[] getTicks() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        long[] ticks = new long[1];
        ticks[0] = osBean.getSystemCpuLoad() *
                (osBean.getAvailableProcessors() * OperatingSystemMXBean.CPU_TICKS_PER_SECOND);
        previousTimestamp = System.nanoTime();
        return ticks;
    }

    /**
     * 测试CPU使用率分析器
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        CpuUsageAnalyzer analyzer = new CpuUsageAnalyzer();
        try {
            while (true) {
                double cpuUsage = analyzer.getCpuUsage();
                System.out.println("Current CPU Usage: " + String.format("%.2f%%", cpuUsage));
                TimeUnit.SECONDS.sleep(1); // 每秒打印一次CPU使用率
            }
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
