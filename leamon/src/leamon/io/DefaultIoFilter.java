package leamon.io;

public class DefaultIoFilter implements IoFilter {

	@Override
	public Object doFilter(IoFilterChain chain) {
		return chain.doFilter();
	}
}
