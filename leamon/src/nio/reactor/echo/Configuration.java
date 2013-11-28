package nio.reactor.echo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import nio.reactor.echo.util.StringUtils;

/**
 * 配置类，读取配置文件。
 */
public class Configuration {
	private static Properties prop;
	private static final String conf = "conf.properties";

	static {
		InputStream in = Configuration.class.getResourceAsStream(conf);
		try {
			prop.load(in);
		} catch (IOException e) {
		}
	}

	public static int getInt(String key) {
		int result = 0;
		if (!StringUtils.isBlank(prop.getProperty(key))) {
			result = Integer.valueOf(prop.getProperty(key));
		}
		return result;
	}
}
