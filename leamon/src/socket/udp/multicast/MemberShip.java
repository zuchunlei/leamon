package socket.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class MemberShip {
	// 多播组地址
	public static SocketAddress multicast_addr = new InetSocketAddress(
			"228.5.6.7", 54321);

	// private Map<Member, SocketAddress> membermap = new
	// ConcurrentHashMap<Member, SocketAddress>();
	// private List<Member> memberlist = new ArrayList<Member>();
	// private long interval = 1000 * 10;

	MulticastSocket multicastSock;
	DatagramSocket sock;

	public void go() throws Exception {
		multicastSock = new MulticastSocket(54321);
		multicastSock.setLoopbackMode(false);
		multicastSock.joinGroup(InetAddress.getByName("228.5.6.7"));
		Timer timer = new Timer("member_receiver");
		timer.scheduleAtFixedRate(new MemberReceiver(multicastSock), 0, 2000);
		sock = new DatagramSocket();
		timer = new Timer("member_sender");
		timer.scheduleAtFixedRate(new MemberSender(sock), 0, 2000);

		// timer = new Timer("self_check");
		// timer.scheduleAtFixedRate(new SelfCheck(), 0, 10000);
	}

	class SelfCheck extends TimerTask {

		@Override
		public void run() {
			// Iterator<Member> iter = membermap.keySet().iterator();
			// long time = System.currentTimeMillis();
			// while (iter.hasNext()) {
			// Member member = iter.next();
			// if (time - member.getTime() > interval) {
			// iter.remove();
			// }
			// }
			// System.out.println(membermap.size());
		}

	}

	/**
	 * 心跳放送线程
	 */
	class MemberSender extends TimerTask {
		DatagramSocket _sock;

		public MemberSender(DatagramSocket sock) {
			this._sock = sock;
		}

		public void run() {

			String time = String.valueOf(System.currentTimeMillis());
			byte[] data = time.getBytes();
			try {
				DatagramPacket pack = new DatagramPacket(data, data.length,
						multicast_addr);
				_sock.send(pack);
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 多播接受线程
	 */
	class MemberReceiver extends TimerTask {
		MulticastSocket _sock;
		byte[] buffer;

		public MemberReceiver(MulticastSocket sock) {
			this._sock = sock;

		}

		public void run() {
			buffer = new byte[16];
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
			try {
				_sock.receive(pack);
				long time = Long.valueOf(new String(pack.getData(), 0, pack
						.getLength()));
				if (pack.getSocketAddress().equals(
						sock.getRemoteSocketAddress())
						|| pack.getSocketAddress().equals(
								sock.getLocalSocketAddress())) {
					System.out.println(time);
				}

				// Member member = new Member(time, pack.getAddress()
				// .getHostAddress(), pack.getPort());
				// membermap.put(member, pack.getSocketAddress());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				buffer = null;
			}
		}
	}
}
