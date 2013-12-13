package leamon.io;

public abstract class BaseIoService implements IoService {

	private IoHandler handler;

	private IoFilterChain filterChain;

	public IoHandler getHandler() {
		return handler;
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

	public void addFilter(IoFilter filter) {
		filterChain.addFilter(filter);
	}

	public void removeFilter(IoFilter filter) {
		filterChain.removeFilter(filter);
	}
}
