package com.bes.ssh.ibatis.dao;

import com.bes.ssh.ibatis.entity.Food;

/**
 * ���ݷ��ʶ���
 */
public interface FoodDao {

	void addFood(Food food);

	Food getFood(int id);

	// List<Food> getAllFood();

}
