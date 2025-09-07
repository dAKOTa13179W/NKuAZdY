// 代码生成时间: 2025-09-07 13:28:14
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public class ApiResponseFormatter {

    private final ObjectMapper objectMapper;

    public ApiResponseFormatter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Helper method to format API response
    private JsonNode formatResponse(Object data, int statusCode) throws JsonProcessingException {
        if (data == null) {
            return objectMapper.createObjectNode().put("status", "error").put("message", "No data provided");
        }
        return objectMapper.createObjectNode().put("status", "success").put("data