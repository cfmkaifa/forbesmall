/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import java.util.List;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.Store;
import net.mall.entity.StoreFavorite;

/**
 * Service - 店铺收藏
 * 
 * @author huanghy
 * @version 6.1
 */
public interface StoreFavoriteService extends BaseService<StoreFavorite, Long> {

	/**
	 * 判断店铺收藏是否存在
	 * 
	 * @param member
	 *            会员
	 * @param store
	 *            店铺
	 * @return 店铺收藏是否存在
	 */
	boolean exists(Member member, Store store);

	/**
	 * 查找店铺收藏
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 店铺收藏
	 */
	List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找店铺收藏
	 * 
	 * @param memberId
	 *            会员ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 店铺收藏
	 */
	List<StoreFavorite> findList(Long memberId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找店铺收藏分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 店铺收藏分页
	 */
	Page<StoreFavorite> findPage(Member member, Pageable pageable);

	/**
	 * 查找店铺收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 店铺收藏数量
	 */
	Long count(Member member);

}