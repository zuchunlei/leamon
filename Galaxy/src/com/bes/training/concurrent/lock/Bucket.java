package com.bes.training.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 容器类，生产者消费者共享容器
 */
public class Bucket {

	private static final int CAPACITY = 1000;// 最大容量

	// 互斥体
	private Lock lock = new ReentrantLock();

	// 条件变量
	private Condition full = lock.newCondition();
	private Condition empty = lock.newCondition();

	private int count;// 当前数量

	/**
	 * put操作，生产行为
	 */
	public void put() {
		try {
			lock.lock();

			while (count >= CAPACITY) {
				try {
					full.await();
				} catch (InterruptedException e) {
					// ignore
				}
			}

			count++;
			System.out.println("put operation , current counter is " + count);
			empty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * take操作，消费行为
	 */
	public void take() {
		try {
			lock.lock();
			while (count <= 0) {
				try {
					empty.await();
				} catch (InterruptedException e) {
					// ignore
				}
			}
			count--;
			System.out.println("take operation , current counter is " + count);
			full.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {

		final Bucket bucket = new Bucket();

		// 消费者
		Runnable takeTask = new Runnable() {
			@Override
			public void run() {
				while (true) {
					bucket.take();
				}
			}
		};

		// 生产者
		Runnable putTask = new Runnable() {
			@Override
			public void run() {
				while (true) {
					bucket.put();
				}
			}
		};

		new Thread(takeTask).start();
		new Thread(takeTask).start();
		new Thread(takeTask).start();
		new Thread(putTask).start();
		new Thread(putTask).start();
	}
}
