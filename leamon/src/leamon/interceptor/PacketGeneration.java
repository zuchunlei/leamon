package leamon.interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ݰ��������� ��Ҫ����Source�ṩ���ֽ����������Ӧ�����������ݰ���������Ͷ�ݡ�
 */
public class PacketGeneration {

	private List<Interceptor> filters;// �������б�
	private Handler handler;// ������
	private InterceptorChain chain;// ��������

	public PacketGeneration() {
		this.filters = new ArrayList<Interceptor>();
		this.handler = new DeliverHandler();
	}

	/**
	 * �������������
	 * 
	 * @param filter
	 */
	public void addFilter(Interceptor filter) {
		filters.add(filter);
	}

	/**
	 * ɾ������������
	 * 
	 * @param filter
	 */
	public void removeFilter(Interceptor filter) {
		filters.remove(filter);
	}

	/**
	 * Ͷ�ݵ���Ӧ��data�洢��
	 * 
	 * @param packet
	 */
	void deliver(MessagePacket packet) {

	}

	/**
	 * �������ݰ���������Ͷ��
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
	 * ����
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
	 * Ͷ�ݴ�����
	 */
	class DeliverHandler implements Handler {
		@Override
		public void handle(MessagePacket packet) {
			deliver(packet);
		}
	}
}
