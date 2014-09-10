package com.bes.ssh.ibatis.dao;

import com.bes.ssh.ibatis.entity.Food;

/**
 * 数据访问对象
 */
public interface FoodDao {

	void addFood(Food food);

	Food getFood(int id);

	// List<Food> getAllFood();

}
