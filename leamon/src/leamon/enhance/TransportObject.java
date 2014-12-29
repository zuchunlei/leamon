package leamon.enhance;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 传输对象，测试transient关键字对java对象序列化的影响
 */
public class TransportObject implements Serializable {

	private static final long serialVersionUID = -8278501218669575448L;

	private int age;

	private transient String name;

	private transient long id;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static void main(String[] args) throws Exception {
		TransportObject obj = new TransportObject();
		obj.setId(100);
		obj.setAge(29);
		obj.setName("zuchunlei");

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				"D:/2.txt"));

		out.writeObject(obj);
		out.close();
	}
}
