// 代码生成时间: 2025-08-18 16:30:01
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 进程管理器
public class ProcessManager extends Application<ProcessManagerConfig> {
    private static final Logger logger = LoggerFactory.getLogger(ProcessManager.class);
    private ScheduledExecutorService executorService;

    public static void main(String[] args) throws Exception {
        new ProcessManager().run(args);
    }

    @Override
    public void initialize(Bootstrap<ProcessManagerConfig> bootstrap) {
        // 初始化配置和视图
        bootstrap.addBundle(new ViewBundle<>());
    }

    @Override
    public void run(ProcessManagerConfig configuration, Environment environment) {
        executorService = Executors.newScheduledThreadPool(configuration.getThreadPoolSize());

        // 启动定时任务检查进程状态
        executorService.scheduleAtFixedRate(this::checkProcessStatus, 0, configuration.getCheckInterval(), TimeUnit.SECONDS);
    }

    // 检查进程状态的方法
    private void checkProcessStatus() {
        try {
            // 获取所有进程信息
            Process[] processes = ProcessHandle.current().info().command().orElse("").isEmpty() ? ProcessHandle.allProcesses().toArray(new Process[0]) : new Process[]{ProcessHandle.current()};
            // 遍历进程并检查状态
            for (Process process : processes) {
                ProcessHandle processHandle = process.onExit().get();
                if (processHandle.isAlive()) {
                    logger.info("Process [{}] is running", processHandle.pid());
                } else {
                    logger.warn("Process [{}] has terminated", processHandle.pid());
                }
            }
        } catch (Exception e) {
            logger.error("Error checking process status", e);
        }
    }

    @Override
    public void run() {
        // 应用停止时关闭定时任务服务
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
