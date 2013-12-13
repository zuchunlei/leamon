package leamon.io;

public class BaseIoService implements IoService {

	private IoHandler handler;
	private IoFilterChain filterChain;

	public BaseIoService() {
		this(0, null);
	}

	public BaseIoService(IoHandler handler) {
		this(0, handler);
	}

	public BaseIoService(int size) {
		this(size, null);
	}

	public BaseIoService(int size, IoHandler handler) {
		this.filterChain = new DefaultIoFilterChain(size, handler);
		this.handler = handler;
	}

	public IoHandler getHandler() {
		return handler;
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
		((DefaultIoFilterChain) filterChain).setHandler(handler);
	}

	public void addFilter(IoFilter filter) {
		filterChain.addFilter(filter);
	}

	public void removeFilter(IoFilter filter) {
		filterChain.removeFilter(filter);
	}

	@Override
	public void read() {
		filterChain.doFilter();
	}

	@Override
	public void write() {
		filterChain.doFilter();
	}
}
