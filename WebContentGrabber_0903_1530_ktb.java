// 代码生成时间: 2025-09-03 15:30:24
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// 网页内容抓取工具类
public class WebContentGrabber {
    private static final Logger logger = LoggerFactory.getLogger(WebContentGrabber.class);

    // 抓取指定URL的网页内容
    public static String fetchWebContent(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 返回网页内容的字符串表示
                    return EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to fetch web content from URL: {}", url, e);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WebContentGrabber <URL>");
            return;
        }

        String url = args[0];
        String content = fetchWebContent(url);
        if (content != null) {
            System.out.println("Web content fetched successfully:
" + content);
        } else {
            System.out.println("Failed to fetch web content.");
        }
    }
}