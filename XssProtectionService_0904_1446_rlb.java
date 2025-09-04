// 代码生成时间: 2025-09-04 14:46:04
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class XssProtectionService {
    private static final Logger logger = LoggerFactory.getLogger(XssProtectionService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String escapeHtml(String input) {
        try {
            if (input == null || input.trim().isEmpty()) {
                return input;
            }
            // 使用Jsoup的Whitelist进行XSS攻击防护
            return Jsoup.clean(input, Whitelist.none());
        } catch (Exception e) {
            logger.error("Error escaping HTML input", e);
            return null;
        }
    }

    public String toJson(String data) {
        try {
            // 将数据转换成JSON格式
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("Error converting data to JSON", e);
            return null;
        }
    }

    public static void main(String[] args) {
        XssProtectionService service = new XssProtectionService();
        String userInput = "<script>alert('XSS')</script>";

        // 清除用户输入中的XSS攻击代码
        String safeInput = service.escapeHtml(userInput);
        logger.info("Safe input: " + safeInput);

        // 将安全的数据转换为JSON
        String jsonData = service.toJson(safeInput);
        logger.info("JSON data: " + jsonData);
    }
}
