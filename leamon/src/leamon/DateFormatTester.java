package leamon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormmat����ת��
 */
public class DateFormatTester {
	private static final String pattern = "yyyy-MM-dd HH:mm:ss";// ����ת����ʽ
	private static SimpleDateFormat format = new SimpleDateFormat(pattern);// ����ת����

	public static void main(String[] args) throws Exception {
		String dateStr = "2012-12-12 29:11:100";

		/*
		 * SimpleDateFormat�ڲ����ʱ�������ȷ��λ���������֤�������ʱ��ֵҲ�ܽ�����ȷ��ת����
		 */
		Date date = format.parse(dateStr);

		System.out.println(date.getTime());
	}
}
