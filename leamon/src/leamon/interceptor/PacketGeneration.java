package leamon.interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据包生成器， 主要根据Source提供的字节数组根据相应规则生产数据包，并进行投递。
 */
public class PacketGeneration {

	private List<Interceptor> filters;// 拦截器列表
	private Handler handler;// 处理器
	private InterceptorChain chain;// 拦截器链

	public PacketGeneration() {
		this.filters = new ArrayList<Interceptor>();
		this.handler = new DeliverHandler();
	}

	/**
	 * 添加拦截器对象
	 * 
	 * @param filter
	 */
	public void addFilter(Interceptor filter) {
		filters.add(filter);
	}

	/**
	 * 删除拦截器对象
	 * 
	 * @param filter
	 */
	public void removeFilter(Interceptor filter) {
		filters.remove(filter);
	}

	/**
	 * 投递到相应的data存储中
	 * 
	 * @param packet
	 */
	void deliver(MessagePacket packet) {

	}

	/**
	 * 生产数据包，并进行投递
	 * 
	 * @param content
	 * @return
	 */
	public void handle(byte[] content) {
		MessagePacket packet = new MessagePacket();
		packet.setBody(content);
		chain.invoke(packet);
	}

	/**
	 * 启动
	 */
	public void start() {
		this.chain = new Node();
	}

	class Node implements InterceptorChain {
		private Interceptor filter;
		private InterceptorChain next;

		Node() {
			this(0);
		}

		Node(int index) {
			if (index < filters.size()) {
				this.filter = filters.get(index);
				this.next = new Node(++index);
			}
		}

		@Override
		public void invoke(MessagePacket packet) {
			if (filter != null) {
				filter.invoke(packet, next);
				return;
			}
			handler.handle(packet);
		}
	}

	/**
	 * 投递处理器
	 */
	class DeliverHandler implements Handler {
		@Override
		public void handle(MessagePacket packet) {
			deliver(packet);
		}
	}
}
