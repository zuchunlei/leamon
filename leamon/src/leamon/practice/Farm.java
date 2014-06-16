package leamon.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * ũ��
 */
public class Farm {

	private List<Cow> cows = new ArrayList<Cow>();// ĸţ����

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
 * ��ţ
 */
class Cow {

	private int age;// ����

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
/* ũ��һСĸţ��ĸţ�������Сĸţ����20���ũ���ж���ͷĸţ�� */