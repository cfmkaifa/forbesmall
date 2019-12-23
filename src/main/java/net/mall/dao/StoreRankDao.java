/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;

import net.mall.Filter;
import net.mall.Order;
import net.mall.entity.StoreRank;

/**
 * Dao - 店铺等级
 * 
 * @author huanghy
 * @version 6.1
 */
public interface StoreRankDao extends BaseDao<StoreRank, Long> {

	/**
	 * 查找店铺等级
	 * 
	 * @param isAllowRegister
	 *            是否允许注册
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 店铺等级
	 */
	List<StoreRank> findList(Boolean isAllowRegister, List<Filter> filters, List<Order> orders);

}