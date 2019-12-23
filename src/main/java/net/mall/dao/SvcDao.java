/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;

import net.mall.Order;
import net.mall.entity.Store;
import net.mall.entity.StoreRank;
import net.mall.entity.Svc;

/**
 * Dao - 服务
 * 
 * @author huanghy
 * @version 6.1
 */
public interface SvcDao extends BaseDao<Svc, Long> {

	/**
	 * 查找服务
	 * 
	 * @param store
	 *            店铺
	 * @param promotionPluginId
	 *            促销插件Id
	 * @param storeRank
	 *            店铺等级
	 * @param orders
	 *            排序
	 * @return 服务
	 */
	List<Svc> find(Store store, String promotionPluginId, StoreRank storeRank, List<Order> orders);

}