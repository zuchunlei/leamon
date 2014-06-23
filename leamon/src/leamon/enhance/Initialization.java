package leamon.enhance;

/**
 * 有关Class类型初始化顺序以及Java对象实例化顺序的总结
 */
public class Initialization {

	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.info();
	}
}

/**
 * 父类
 */
class Sup {

	private static String SUP_NAME = "Sup";

	static {
		SUP_NAME = "SUP_NAME";
	}

	private String name = "zuchunlei";

	public Sup() {
		this.name = "panda";
	}

	public void info() {
		System.out.println("Sup_Name is " + SUP_NAME);
		System.out.println("name is " + this.name);
	}

}

/**
 * 子类
 */
class Sub extends Sup {

	private static String SUB_NAME = "Sub";

	static {
		SUB_NAME = "SUB_NAME";
	}

	public Sub() {
		// this.name = "denver";
	}

	{
		name = "mickey";
	}

	private String name = "foxli";

	public void info() {
		super.info();
		System.out.println("Sub_Name is " + SUB_NAME);
		System.out.println("name is " + this.name);
	}

}
