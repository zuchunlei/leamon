package com.bes.training;

public class BreadConsumer implements Runnable {
	private BreadContainer bread;
	private int eatnum;

	public BreadConsumer(BreadContainer bread, int eatnum) {
		this.bread = bread;
		this.eatnum = eatnum;
	}

	public void run() {
		bread.eat(eatnum);
	}

}
