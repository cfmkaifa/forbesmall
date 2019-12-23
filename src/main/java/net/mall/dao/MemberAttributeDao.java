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
import net.mall.entity.MemberAttribute;

/**
 * Dao - 会员注册项
 * 
 * @author huanghy
 * @version 6.1
 */
public interface MemberAttributeDao extends BaseDao<MemberAttribute, Long> {

	/**
	 * 查找未使用的属性序号
	 * 
	 * @return 未使用的属性序号，若不存在则返回null
	 */
	Integer findUnusedPropertyIndex();

	/**
	 * 查找会员注册项
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 会员注册项
	 */
	List<MemberAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders);

}