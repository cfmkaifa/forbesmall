/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import net.mall.entity.OrderPayment;

/**
 * Service - 订单支付
 * 
 * @author huanghy
 * @version 6.1
 */
public interface OrderPaymentService extends BaseService<OrderPayment, Long> {

	/**
	 * 根据编号查找订单支付
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单支付，若不存在则返回null
	 */
	OrderPayment findBySn(String sn);

}