package io;

import java.io.FileOutputStream;

/**
 * 文件流测试
 */
public class FileStreamTester {

	private static final int BUFFER_SIZE = 8192;

	private byte[] data = new byte[10 * 1024 * 1024];// 10M字节

	{
		for (int i = 0; i < data.length; i++) {// 初始化数据
			data[i] = 100;
		}
	}

	/**
	 * 打开文件流，直接写10M数据
	 * 
	 * @throws Exception
	 */
	public void directIO() throws Exception {// 94毫秒
		long start = System.currentTimeMillis();
		FileOutputStream out = new FileOutputStream("D:/1.txt", true);
		out.write(data, 0, data.length);
		out.close();
		System.out.println(System.currentTimeMillis() - start);
	}

	/**
	 * 以8K为缓冲，重复开启文件，关闭文件，进行IO
	 * 
	 * @throws Exception
	 */
	public void repeatIO() throws Exception {// 1000毫秒(8K) 110(1M)
		byte[] buffer = new byte[BUFFER_SIZE];
		for (int i = 0; i < BUFFER_SIZE; i++) {
			buffer[i] = 1;
		}

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1280; i++) {
			FileOutputStream out = new FileOutputStream("D:/1.txt", true);
			out.write(buffer, 0, buffer.length);
			out.close();
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void main(String[] args) throws Exception {
		FileStreamTester tester = new FileStreamTester();
		tester.repeatIO();
	}
}
