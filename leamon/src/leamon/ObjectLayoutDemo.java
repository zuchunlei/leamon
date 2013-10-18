package leamon;

public class ObjectLayoutDemo {

	public static void main(String[] args) {
		TestObject obj = new TestObject(false, 28, "×æ´ºÀ×", 1L, (short) 10,
				100.01D);

		System.out.println(obj);
	}

}

class TestObject {
	private boolean sex;
	private int age;
	private String name;
	private long id;
	private short favior;
	private double price;

	public TestObject(boolean sex, int age, String name, long id, short favior,
			double price) {
		super();
		this.sex = sex;
		this.age = age;
		this.name = name;
		this.id = id;
		this.favior = favior;
		this.price = price;
	}

	public boolean isSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public short getFavior() {
		return favior;
	}

	public double getPrice() {
		return price;
	}
}