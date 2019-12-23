/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.ShippingMethod;

/**
 * Dao - 配送方式
 * 
 * @author huanghy
 * @version 6.1
 */
public interface ShippingMethodDao extends BaseDao<ShippingMethod, Long> {

	/**
	 * 查找配送方式分页
	 * 
	 * @param pageable
	 *            分页
	 * @return 配送方式分页
	 */
	Page<ShippingMethod> findPage(Pageable pageable);
}