// 代码生成时间: 2025-08-25 20:34:26
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import io.dropwizard.views.mustache.MustacheTemplateLocator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

// 主类，继承自Application
public class UrlValidator extends Application<UrlValidatorConfiguration> {

    // 主方法，程序入口
    public static void main(String[] args) throws Exception {
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.run(args);
    }

    // 运行方法
    @Override
    public void run(UrlValidatorConfiguration configuration, Environment environment) throws Exception {
        // 注册资源
        environment.jersey().register(new UrlValidationResource());
    }
}

// 配置类
class UrlValidatorConfiguration extends Configuration {
    // 配置项
}

// 资源类，处理HTTP请求
@Path("/validate")
public class UrlValidationResource {

    // GET请求，验证URL有效性
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUrl(@QueryParam("url") String url) {
        if (url == null || url.isEmpty()) {
            // 错误处理：URL为空
            return Response.status(Response.Status.BAD_REQUEST).entity("URL cannot be empty.").build();
        }
        try {
            // 验证URL有效性
            boolean isUrlValid = isValidUrl(url);
            // 返回结果
            return Response.ok().entity(isUrlValid).build();
        } catch (Exception e) {
            // 错误处理：验证过程中的异常
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred while validating URL.").build();
        }
    }

    // 验证URL有效性的方法
    private boolean isValidUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法
            connection.setRequestMethod("HEAD");
            // 获取响应码
            int responseCode = connection.getResponseCode();
            // 根据响应码判断URL有效性
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_TEMP;
        } catch (Exception e) {
            // 异常处理：URL格式错误或连接问题
            return false;
        }
    }
}
