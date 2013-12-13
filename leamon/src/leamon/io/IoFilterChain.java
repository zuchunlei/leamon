package leamon.io;

public interface IoFilterChain {

	Object doFilter();

	void addFilter(IoFilter filter);

	void removeFilter(IoFilter filter);
}
