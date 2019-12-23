/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Aftersales;
import net.mall.entity.Member;
import net.mall.entity.OrderItem;
import net.mall.entity.Store;

/**
 * Dao - 售后
 * 
 * @author huanghy
 * @version 6.1
 */
public interface AftersalesDao extends BaseDao<Aftersales, Long> {

	/**
	 * 查找售后列表
	 * 
	 * @param orderItems
	 *            订单项
	 * @return 售后列表
	 */
	List<Aftersales> findList(List<OrderItem> orderItems);

	/**
	 * 查找售后分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 售后分页
	 */
	Page<Aftersales> findPage(Aftersales.Type type, Aftersales.Status status, Member member, Store store, Pageable pageable);

}