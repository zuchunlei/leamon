package socket.udp.multicast;

import java.io.Serializable;

public class Member implements Serializable {

	private static final long serialVersionUID = 3896732313915099535L;

	private long time;
	private String address;
	private int port;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Member(long time, String address, int port) {
		this.time = time;
		this.address = address;
		this.port = port;
	}

	public Member() {
	}

	@Override
	public int hashCode() {
		return address.hashCode() + port;
	}

	@Override
	public boolean equals(Object obj) {
		Member m = (Member) obj;
		return address.equals(m.address) && port == m.port;
	}

}
