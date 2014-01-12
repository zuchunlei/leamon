package leamon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormmat日期转换
 */
public class DateFormatTester {
	private static final String pattern = "yyyy-MM-dd HH:mm:ss";// 日期转换格式
	private static SimpleDateFormat format = new SimpleDateFormat(pattern);// 日期转换器

	public static void main(String[] args) throws Exception {
		String dateStr = "2012-12-12 29:11:100";

		/*
		 * SimpleDateFormat内部会对时间进行正确进位与调整，保证不规则的时间值也能进行正确的转化。
		 */
		Date date = format.parse(dateStr);

		System.out.println(date.getTime());
	}
}
