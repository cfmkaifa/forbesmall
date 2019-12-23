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
import net.mall.entity.InstantMessage;
import net.mall.entity.Store;

/**
 * Service - 即时通讯
 * 
 * @author huanghy
 * @version 6.1
 */
public interface InstantMessageService extends BaseService<InstantMessage, Long> {

	/**
	 * 查找即时通讯
	 *
	 * @param type
	 *            类型
	 * @param storeId
	 *            店铺ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 即时通讯
	 */
	List<InstantMessage> findList(InstantMessage.Type type, Long storeId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找即时通讯分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 即时通讯分页
	 */
	Page<InstantMessage> findPage(Store store, Pageable pageable);

}