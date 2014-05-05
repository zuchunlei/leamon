package leamon.url;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ���HttpURLConnection����Ӧ�ض���url�Ĳ���
 */
public class HttpURLRedirectTester {

	public static void main(String[] args) throws Exception {

		String path = "http://localhost:8080/demo/redirect";
		URL url = new URL(path);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// ����HttpURLConnectionʵ���Ƿ�Ӧ���Զ�ִ�� HTTP�ض���(��Ӧ����Ϊ 3xx������)
		// ��Ĭ�������Ϊ true
		conn.setInstanceFollowRedirects(false);

		conn.connect();

		int code = conn.getResponseCode();
		String message = conn.getResponseMessage();
		
		System.out.println(code + " : " + message);
	}

}
