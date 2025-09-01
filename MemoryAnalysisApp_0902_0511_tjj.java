// 代码生成时间: 2025-09-02 05:11:58
package com.example.memoryanalysis;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemoryAnalysisApp extends Application<MemoryAnalysisAppConfig> {

    /*
     * Runs the Dropwizard application.
     */
    @Override
    public void run(MemoryAnalysisAppConfig configuration, Environment environment) {
        try {
            // Registering the MemoryUsageResource
            environment.jersey().register(new MemoryUsageResource());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    /*
     * Main method to start the application.
     */
    public static void main(String[] args) throws Exception {
        new MemoryAnalysisApp().run(args);
    }
}

/**
 * MemoryUsageResource.java
 * 
 * Resource class to handle memory usage data.
 */
package com.example.memoryanalysis;

import io.dropwizard.jersey.errors.ErrorMessage;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Path("/memory")
public class MemoryUsageResource {
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemoryUsage() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            // Returning memory usage data as JSON
            return Response.ok(buildMemoryUsageResponse(heapMemoryUsage, nonHeapMemoryUsage)).build();
        } catch (Exception e) {
            // In case of any error, return an error message
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("500", "Internal Server Error")).build();
        }
    }

    private MemoryUsageData buildMemoryUsageResponse(MemoryUsage heapMemoryUsage, MemoryUsage nonHeapMemoryUsage) {
        // Constructing the response object
        return new MemoryUsageData(
                heapMemoryUsage.getInit(),
                heapMemoryUsage.getUsed(),
                heapMemoryUsage.getCommitted(),
                heapMemoryUsage.getMax(),
                nonHeapMemoryUsage.getInit(),
                nonHeapMemoryUsage.getUsed(),
                nonHeapMemoryUsage.getCommitted(),
                nonHeapMemoryUsage.getMax()
        );
    }
}

/**
 * MemoryUsageData.java
 * 
 * Data class to hold memory usage data.
 */
package com.example.memoryanalysis;

public class MemoryUsageData {

    private final long heapInit;
    private final long heapUsed;
    private final long heapCommitted;
    private final long heapMax;
    private final long nonHeapInit;
    private final long nonHeapUsed;
    private final long nonHeapCommitted;
    private final long nonHeapMax;

    public MemoryUsageData(long heapInit, long heapUsed, long heapCommitted, long heapMax,
                            long nonHeapInit, long nonHeapUsed, long nonHeapCommitted, long nonHeapMax) {
        this.heapInit = heapInit;
        this.heapUsed = heapUsed;
        this.heapCommitted = heapCommitted;
        this.heapMax = heapMax;
        this.nonHeapInit = nonHeapInit;
        this.nonHeapUsed = nonHeapUsed;
        this.nonHeapCommitted = nonHeapCommitted;
        this.nonHeapMax = nonHeapMax;
    }

    // Getter methods for each field
    // ...
}
