package leamon.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * 农场
 */
public class Farm {

	private List<Cow> cows = new ArrayList<Cow>();// 母牛集合

	public Farm() {
		cows.add(new Cow());
	}

	public void grow() {
		List<Cow> list = new ArrayList<Cow>();

		for (Cow cow : cows) {
			Cow child = cow.grow();

			if (child != null) {
				list.add(child);
			}
			list.add(cow);
		}

		this.cows = list;
	}

	public int count() {
		return cows.size();
	}

	public static void main(String[] args) {
		Farm farm = new Farm();

		for (int i = 0; i < 20; i++) {
			farm.grow();
		}

		System.out.println(farm.count());
	}
}

/**
 * 奶牛
 */
class Cow {

	private int age;// 年龄

	public Cow() {
		age = 0;
	}

	public Cow grow() {
		Cow child = null;

		if (++age >= 4) {
			child = new Cow();
		}

		return child;
	}

}
/* 农场一小母牛，母牛四岁可生小母牛。问20年后，农场有多少头母牛？ */