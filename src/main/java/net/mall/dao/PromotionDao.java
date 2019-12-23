/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;
import java.util.Set;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.MemberRank;
import net.mall.entity.ProductCategory;
import net.mall.entity.Promotion;
import net.mall.entity.Store;
import net.mall.plugin.PromotionPlugin;

/**
 * Dao - 促销
 * 
 * @author huanghy
 * @version 6.1
 */
public interface PromotionDao extends BaseDao<Promotion, Long> {

	/**
	 * 通过名称查找促销
	 * 
	 * @param keyword
	 *            关键词
	 * @param excludes
	 *            排除促销
	 * @param count
	 *            数量
	 * @return 促销
	 */
	List<Promotion> search(String keyword, Set<Promotion> excludes, Integer count);

	/**
	 * 查找促销
	 * 
	 * @param promotionPlugin
	 *            促销插件
	 * @param store
	 *            店铺
	 * @param isEnabled
	 *            是否开启
	 * @return 促销
	 */
	List<Promotion> findList(PromotionPlugin promotionPlugin, Store store, Boolean isEnabled);

	/**
	 * 查找促销
	 * 
	 * @param promotionPlugin
	 *            促销插件
	 * @param store
	 *            店铺
	 * @param memberRank
	 *            会员等级
	 * @param productCategory
	 *            商品分类
	 * @param hasBegun
	 *            是否已开始
	 * @param hasEnded
	 *            是否已结束
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 促销
	 */
	List<Promotion> findList(PromotionPlugin promotionPlugin, Store store, MemberRank memberRank, ProductCategory productCategory, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找促销
	 * 
	 * @param promotionPlugin
	 *            促销插件
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 促销分页
	 */
	Page<Promotion> findPage(PromotionPlugin promotionPlugin, Store store, Pageable pageable);

}