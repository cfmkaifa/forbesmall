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
import net.mall.entity.Attribute;
import net.mall.entity.ProductCategory;

/**
 * Dao - 属性
 * 
 * @author huanghy
 * @version 6.1
 */
public interface AttributeDao extends BaseDao<Attribute, Long> {

	/**
	 * 查找未使用的属性序号
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 未使用的属性序号，若不存在则返回null
	 */
	Integer findUnusedPropertyIndex(ProductCategory productCategory);

	/**
	 * 查找属性
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 属性
	 */
	List<Attribute> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders);

}