package leamon.io;

public interface IoFilter {

	Object doFilter(IoFilterChain chain);

}
