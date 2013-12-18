package leamon.io;

public class DefaultIoFilter implements IoFilter {

	private static int index = 0;

	private int number;

	public DefaultIoFilter() {
		this.number = index++;
	}

	@Override
	public Object doFilter(IoFilterChain chain) {
		System.out.println("IoFilter number is :" + number);
		chain.doFilter();
		return null;
	}
}
