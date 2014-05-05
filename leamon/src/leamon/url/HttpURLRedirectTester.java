package leamon.url;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 添加HttpURLConnection对响应重定向url的测试
 */
public class HttpURLRedirectTester {

	public static void main(String[] args) throws Exception {

		String path = "http://localhost:8080/demo/redirect";
		URL url = new URL(path);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置HttpURLConnection实例是否应该自动执行 HTTP重定向(响应代码为 3xx的请求)
		// 其默认情况下为 true
		conn.setInstanceFollowRedirects(false);

		conn.connect();

		int code = conn.getResponseCode();
		String message = conn.getResponseMessage();
		
		System.out.println(code + " : " + message);
	}

}
