package socket.data;

/**
 * 0xff 默认是int类型，byte & 0xFF 返回的是一个int值，其值也就是byte代表的无符号值。
 * int类型的数值转化为byte类型时，直接将高24位舍弃，保留低8位作为byte类型的值。
 */
public class DataTester {

	public static void main(String[] args) {
		byte b = -12;
		// b && 0xff 返回的类型是 int
		System.out.println(b & 0xff);

		// int --> byte
		int num = -2021956;
		byte bn = (byte) num;
		System.out.println(bn);
	}
}
