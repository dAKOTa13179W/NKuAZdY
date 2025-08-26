// 代码生成时间: 2025-08-27 03:05:29
import com.dropwizard.Application;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.dropwizard.views.View;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/memory")
public class MemoryAnalysisResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MemoryAnalysisResult getMemoryAnalysis() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        return new MemoryAnalysisResult(heapMemoryUsage, nonHeapMemoryUsage);
    }
}

public class MemoryAnalysisResult {
    private final MemoryUsage heapMemoryUsage;
    private final MemoryUsage nonHeapMemoryUsage;

    public MemoryAnalysisResult(MemoryUsage heapMemoryUsage, MemoryUsage nonHeapMemoryUsage) {
        this.heapMemoryUsage = heapMemoryUsage;
        this.nonHeapMemoryUsage = nonHeapMemoryUsage;
    }

    public MemoryUsage getHeapMemoryUsage() {
        return heapMemoryUsage;
    }

    public MemoryUsage getNonHeapMemoryUsage() {
        return nonHeapMemoryUsage;
    }
}

public class MemoryAnalysisApp extends Application<MemoryAnalysisConfiguration> {

    @Override
    public void initialize(Bootstrap<MemoryAnalysisConfiguration> bootstrap) {
        // Initialize any additional components here.
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
        bootstrap.addBundle(new ViewBundle<MemoryAnalysisConfiguration>()
            .addRenderer(new FreemarkerViewRenderer())
            .addResourceHandler("/views/", "/views"));
    }

    @Override
    public void run(MemoryAnalysisConfiguration configuration, Environment environment) {
        // Register your resources here.
        environment.jersey().register(new MemoryAnalysisResource());
    }
}

// The main method to start the Dropwizard application.
public static void main(String[] args) throws Exception {
    new MemoryAnalysisApp().run(args);
}