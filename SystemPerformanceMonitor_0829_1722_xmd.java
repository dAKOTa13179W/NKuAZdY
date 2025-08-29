// 代码生成时间: 2025-08-29 17:22:19
// SystemPerformanceMonitor.java
import com.dropwizard.Application;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.dropwizard.views.View;
import com.dropwizard.views.ViewBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;

@Path("/performance")
public class SystemPerformanceMonitor {

    private final OperatingSystemMXBean osBean;
    private final RuntimeMXBean runtimeBean;
    private final ThreadMXBean threadBean;

    public SystemPerformanceMonitor() {
        osBean = ManagementFactory.getOperatingSystemMXBean();
        runtimeBean = ManagementFactory.getRuntimeMXBean();
        threadBean = ManagementFactory.getThreadMXBean();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getSystemPerformance() {
        Map<String, Object> performanceData = Map.of(
            "cpuLoad", osBean.getSystemCpuLoad(),
            "processCpuLoad", osBean.getProcessCpuLoad(),
            "freeMemory", runtimeBean.getFreeMemory() / (1024 * 1024),
            "totalMemory", runtimeBean.getTotalMemory() / (1024 * 1024),
            "threadCount", threadBean.getThreadCount(),
            "peakThreadCount", threadBean.getPeakThreadCount(),
            "daemonThreadCount", threadBean.getDaemonThreadCount()
        );

        return performanceData;
    }

    public static class PerformanceMonitorApplication extends Application<PerformanceMonitorConfiguration> {

        public static void main(String[] args) throws Exception {
            new PerformanceMonitorApplication().run(args);
        }

        @Override
        public void initialize(Bootstrap<PerformanceMonitorConfiguration> bootstrap) {
            bootstrap.addBundle(new AssetsBundle("/", "/"));
            bootstrap.addBundle(new ViewBundle<PerformanceMonitorConfiguration>() {
                @Override
                public Class<PerformanceMonitorConfiguration> getConfigurationClass() {
                    return PerformanceMonitorConfiguration.class;
                }

                @Override
                public Renderer getRenderer() {
                    return new FreemarkerViewRenderer();
                }
            });
        }

        @Override
        public void run(PerformanceMonitorConfiguration configuration, Environment environment) {
            // Register the resource class
            environment.jersey().register(new SystemPerformanceMonitor());
        }
    }

    public static class PerformanceMonitorConfiguration extends Configuration {
    }
}