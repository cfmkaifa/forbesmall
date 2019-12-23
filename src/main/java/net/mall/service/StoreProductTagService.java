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
import net.mall.entity.Store;
import net.mall.entity.StoreProductTag;

/**
 * Service - 店铺商品标签
 * 
 * @author huanghy
 * @version 6.1
 */
public interface StoreProductTagService extends BaseService<StoreProductTag, Long> {

	/**
	 * 查找店铺商品标签
	 * 
	 * @param store
	 *            店铺
	 * @param isEnabled
	 *            是否启用
	 * @return 店铺商品标签
	 */
	List<StoreProductTag> findList(Store store, Boolean isEnabled);

	/**
	 * 查找店铺商品标签
	 * 
	 * @param storeId
	 *            店铺ID
	 * @param isEnabled
	 *            是否启用
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 店铺商品标签
	 */
	List<StoreProductTag> findList(Long storeId, Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找店铺商品标签分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 店铺商品标签分页
	 */
	Page<StoreProductTag> findPage(Store store, Pageable pageable);

}