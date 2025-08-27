// 代码生成时间: 2025-08-28 06:16:59
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
# 添加错误处理
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
# NOTE: 重要实现细节
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

// 自定义的访问权限控制Application类
public class AccessControlApplication extends Application<AccessControlConfiguration> {

    // 定义访问权限控制的配置类
    public static class AccessControlConfiguration extends Configuration {
        // 这里可以添加配置属性，例如用户角色等
    }

    public static void main(String[] args) throws Exception {
        new AccessControlApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AccessControlConfiguration> bootstrap) {
        // 初始化时不需要额外配置
    }

    @Override
    public void run(AccessControlConfiguration configuration, Environment environment) {
        // 设置CrossOriginFilter以支持跨域请求
        FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter(CrossOriginFilter.class, "/*");
# NOTE: 重要实现细节
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
# 优化算法效率
        corsFilter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
# 改进用户体验
        corsFilter.setAsyncSupported(true);
# 优化算法效率
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // 添加视图支持，用于返回视图模板
        environment.jersey().register(new ViewMessageBodyWriter<>());
        environment.jersey().register(new ViewBundle());
    }
}
