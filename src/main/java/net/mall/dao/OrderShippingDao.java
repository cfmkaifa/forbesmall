/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import net.mall.entity.Order;
import net.mall.entity.OrderShipping;

/**
 * Dao - 订单发货
 * 
 * @author huanghy
 * @version 6.1
 */
public interface OrderShippingDao extends BaseDao<OrderShipping, Long> {

	/**
	 * 查找最后一条订单发货
	 * 
	 * @param order
	 *            订单
	 * @return 订单发货，若不存在则返回null
	 */
	OrderShipping findLast(Order order);

}