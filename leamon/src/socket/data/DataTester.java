package socket.data;

/**
 * 0xff Ĭ����int���ͣ�byte & 0xFF ���ص���һ��intֵ����ֵҲ����byte������޷���ֵ��
 * int���͵���ֵת��Ϊbyte����ʱ��ֱ�ӽ���24λ������������8λ��Ϊbyte���͵�ֵ��
 */
public class DataTester {

	public static void main(String[] args) {
		byte b = -12;
		// b && 0xff ���ص������� int
		System.out.println(b & 0xff);

		// int --> byte
		int num = -2021956;
		byte bn = (byte) num;
		System.out.println(bn);
	}
}
