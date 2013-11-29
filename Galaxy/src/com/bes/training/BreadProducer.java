package com.bes.training;

public class BreadProducer implements Runnable {
	private BreadContainer bread;
	private int eatnum;

	public BreadProducer(BreadContainer bread, int eatnum) {
		this.bread = bread;
		this.eatnum = eatnum;
	}

	public void run() {
		bread.buy(eatnum);
	}

}
