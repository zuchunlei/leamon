package com.bes.training.bread;


public class BreadMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BreadContainer bread = new BreadContainer(0, 20);
		BreadConsumer bc1 = new BreadConsumer(bread, 1);
		BreadConsumer bc2 = new BreadConsumer(bread, 2);
		BreadConsumer bc3 = new BreadConsumer(bread, 3);

		BreadProducer bp1 = new BreadProducer(bread, 1);
		BreadProducer bp2 = new BreadProducer(bread, 2);
		BreadProducer bp3 = new BreadProducer(bread, 3);

		new Thread(bc1).start();
		new Thread(bc2).start();
		new Thread(bc3).start();

		new Thread(bp1).start();
		new Thread(bp2).start();
		new Thread(bp3).start();
	}

}
