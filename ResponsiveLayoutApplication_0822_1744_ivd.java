// 代码生成时间: 2025-08-22 17:44:35
import com.dropwizard.Application;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.dropwizard.views.View;
import com.dropwizard.views.mustache.MustacheViewRenderer;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.EnumSet;

// 定义资源类
@Path("/api/responsive")
public class ResponsiveResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getResponsiveLayout() {
        // 返回一个简单的HTML响应，用于展示响应式布局
        return Response.ok("<html><body>
" +
                "<div style='width: 100%; padding: 10px; box-sizing: border-box;'>
" +
                "  <h1>响应式布局示例</h1>
" +
                "  <p>这是一个响应式布局的示例。</p>
" +
                "</div>
" +
                "</body></html>").build();
    }
}

// 定义主应用程序类
public class ResponsiveLayoutApplication extends Application<ResponsiveLayoutConfiguration> {

    public static void main(String[] args) throws Exception {
        new ResponsiveLayoutApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ResponsiveLayoutConfiguration> bootstrap) {
        // 初始化配置
        bootstrap.addBundle(new ViewBundle<ResponsiveLayoutConfiguration.ViewConfiguration>() {
            @Override
            public void run(ResponsiveLayoutConfiguration.ViewConfiguration configuration, Environment environment) {
                environment.getViewRenderers().register(new MustacheViewRenderer());
            }
        });
    }

    @Override
    public void run(ResponsiveLayoutConfiguration configuration, Environment environment) {
        // 设置CORS过滤器以允许跨域请求
        FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
        corsFilter.setInitParameter("allowedOrigins", "*");
        corsFilter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS");
        corsFilter.setInitParameter("allowedHeaders", "Content-Type,Authorization");
        corsFilter.setInitParameter("preflightMaxAge", "1728000"); // 20天

        // 注册资源
        environment.jersey().register(new ResponsiveResource());
    }
}
