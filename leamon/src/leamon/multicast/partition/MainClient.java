package leamon.multicast.partition;

public class MainClient {

	public static void main(String[] args) {
		Downloader downloador = new Downloader();
		String src = args[0];
		String dest = args[1];
		long start = System.currentTimeMillis();
		downloador.download(src, dest);
		System.out.println("total cost time is :"
				+ (System.currentTimeMillis() - start));
	}
}
