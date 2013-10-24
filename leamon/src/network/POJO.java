package network;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class POJO implements Serializable {
	private static final long serialVersionUID = -8856108098279177787L;

	private int id;
	private String name;

	public POJO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static void main(String[] args) {
		try {
			// FileOutputStream os = new FileOutputStream("F://1.txt");
			// ObjectOutputStream oos = new ObjectOutputStream(os);
			// POJO pojo = new POJO(1, "zuchunlei");
			// oos.writeObject(pojo);
			// oos.close();
			FileInputStream in = new FileInputStream("F://1.txt");
			ObjectInputStream ois = new ObjectInputStream(in);
			POJO pojo = (POJO) ois.readObject();
			System.out.println(pojo);
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
