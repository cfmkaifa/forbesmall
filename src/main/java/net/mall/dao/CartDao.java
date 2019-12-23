/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import net.mall.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author huanghy
 * @version 6.1
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 删除过期购物车
	 */
	void deleteExpired();

}