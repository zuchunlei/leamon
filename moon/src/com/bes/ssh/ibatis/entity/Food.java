package com.bes.ssh.ibatis.entity;

import java.io.Serializable;

/**
 * Ê³Îï
 */
public class Food implements Serializable {

	private static final long serialVersionUID = -286349152730946277L;

	private int id;
	private String name;
	private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Food(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public Food() {
	}

}
