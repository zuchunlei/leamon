package com.bes.training;

/**
 * 馒头集装箱
 * 
 * @author zhaowei
 * 
 */
public class BreadContainer {
	private int currentIndex;
	private int currentSize;

	BreadContainer(int currentIndex, int currentSize) {
		this.currentIndex = currentIndex;
		this.currentSize = currentSize;
	}

	public synchronized void eat(int count) {
		while (currentIndex < count) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		currentIndex = currentIndex - count;
		this.notify();
		System.out.println(Thread.currentThread() + "吃了" + count + "个馒头，剩下"
				+ currentIndex);
	}

	public synchronized void buy(int count) {
		while (currentIndex + count >= currentSize) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		currentIndex = currentIndex + count;
		this.notify();
		System.out.println(Thread.currentThread() + "买了" + count + "个馒头，一共有"
				+ currentIndex);
	}
}
